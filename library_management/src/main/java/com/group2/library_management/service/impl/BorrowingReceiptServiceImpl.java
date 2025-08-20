package com.group2.library_management.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.group2.library_management.dto.response.BorrowingReceiptResponse;
import com.group2.library_management.entity.BookInstance;
import com.group2.library_management.entity.BorrowingReceipt;
import com.group2.library_management.entity.Edition;
import com.group2.library_management.entity.User;
import com.group2.library_management.entity.enums.BookStatus;
import com.group2.library_management.entity.enums.BorrowingStatus;
import com.group2.library_management.repository.BookInstanceRepository;
import com.group2.library_management.repository.BorrowingReceiptRepository;
import com.group2.library_management.repository.EditionRepository;
import com.group2.library_management.service.BorrowingReceiptService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BorrowingReceiptServiceImpl implements BorrowingReceiptService {

    private static final String APPROVAL_ERROR_PREFIX =
            "Không thể phê duyệt: Các sách sau không còn sẵn sàng: ";
    private static final String RECEIPT_NOT_FOUND_ERROR = "Không tìm thấy phiếu mượn với ID: ";
    private static final String PENDING_STATUS_REQUIRED_ERROR =
            "Chỉ có thể phê duyệt yêu cầu đang ở trạng thái chờ phê duyệt";
    private static final String REJECTION_STATUS_REQUIRED_ERROR =
            "Chỉ có thể từ chối yêu cầu đang ở trạng thái chờ phê duyệt";
    private static final String BORROWED_STATUS_REQUIRED_ERROR =
            "Chỉ có thể trả sách khi phiếu mượn đang ở trạng thái đã mượn";

    private final BorrowingReceiptRepository borrowingReceiptRepository;
    private final BookInstanceRepository bookInstanceRepository;
    private final EditionRepository editionRepository;

    @Override
    public Page<BorrowingReceiptResponse> getAllBorrowingRequests(String keyword,
            BorrowingStatus status, Pageable pageable) {
        Specification<BorrowingReceipt> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query != null && query.getResultType() != Long.class
                    && query.getResultType() != long.class) {
                root.fetch("user");
            }

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (StringUtils.hasText(keyword)) {
                Join<BorrowingReceipt, User> userJoin = root.join("user");
                String pattern = "%" + keyword.toLowerCase().trim() + "%";
                predicates.add(cb.or(cb.like(cb.lower(userJoin.get("name")), pattern),
                        cb.like(cb.lower(userJoin.get("email")), pattern)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<BorrowingReceipt> receiptPage = borrowingReceiptRepository.findAll(spec, pageable);
        return receiptPage.map(BorrowingReceiptResponse::fromEntity);
    }

    @Override
    public BorrowingReceiptResponse getBorrowingRequestById(Integer id) {
        BorrowingReceipt receipt = borrowingReceiptRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy phiếu mượn với ID:" + " " + id));

        return BorrowingReceiptResponse.fromEntity(receipt, true);
    }

    @Override
    @Transactional
    public void approveBorrowingRequest(Integer id) {
        BorrowingReceipt receipt = borrowingReceiptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(RECEIPT_NOT_FOUND_ERROR + id));

        // Check current status
        if (receipt.getStatus() != BorrowingStatus.PENDING) {
            throw new IllegalStateException(PENDING_STATUS_REQUIRED_ERROR);
        }

        // Validate all book instances are available before approving
        List<BookInstance> unavailableBooks = receipt.getBorrowingDetails().stream()
                .map(detail -> detail.getBookInstance())
                .filter(bookInstance -> bookInstance.getStatus() != BookStatus.AVAILABLE).toList();

        // If any book is not available, reject the approval
        if (!unavailableBooks.isEmpty()) {
            String errorMessage = APPROVAL_ERROR_PREFIX + unavailableBooks.stream()
                    .map(this::formatBookInstanceError).collect(Collectors.joining(", "));
            throw new IllegalStateException(errorMessage);
        }

        // All books are available, proceed with approval
        // Update borrowing receipt status
        receipt.setStatus(BorrowingStatus.APPROVED);

        // Update status of all book instances and decrease availableQuantity
        receipt.getBorrowingDetails().forEach(detail -> {
            BookInstance bookInstance = detail.getBookInstance();
            Edition edition = bookInstance.getEdition();

            // Update book instance status: AVAILABLE → RESERVED (when approve request)
            bookInstance.setStatus(BookStatus.RESERVED);
            bookInstanceRepository.save(bookInstance);

            // Check availableQuantity before decreasing
            if (edition.getAvailableQuantity() <= 0) {
                throw new IllegalStateException(
                        "Số lượng sách khả dụng không đủ để cho mượn: " + edition.getTitle());
            }
            edition.setAvailableQuantity(edition.getAvailableQuantity() - 1);
            editionRepository.save(edition); // Save edition to update availableQuantity
        });

        borrowingReceiptRepository.save(receipt);

        // TODO: Send email notification to user
        // emailService.sendApprovalNotification(receipt.getUser().getEmail(), receipt);
    }

    @Override
    @Transactional
    public void rejectBorrowingRequest(Integer id, String rejectedReason) {
        BorrowingReceipt receipt = borrowingReceiptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(RECEIPT_NOT_FOUND_ERROR + id));

        // Check current status
        if (receipt.getStatus() != BorrowingStatus.PENDING) {
            throw new IllegalStateException(REJECTION_STATUS_REQUIRED_ERROR);
        }

        // Update borrowing receipt status and rejection reason
        receipt.setStatus(BorrowingStatus.REJECTED);
        receipt.setRejectedReason(rejectedReason);

        // Save changes
        borrowingReceiptRepository.save(receipt);

        // TODO: Send email notification to user
        // emailService.sendRejectionNotification(receipt.getUser().getEmail(), receipt);
    }

    @Override
    @Transactional
    public void returnBook(Integer id) {
        BorrowingReceipt receipt = borrowingReceiptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(RECEIPT_NOT_FOUND_ERROR + id));

        // Check current status - must be BORROWED to return
        if (receipt.getStatus() != BorrowingStatus.BORROWED) {
            throw new IllegalStateException(BORROWED_STATUS_REQUIRED_ERROR);
        }

        // Update status of all book instances and increase availableQuantity
        receipt.getBorrowingDetails().forEach(detail -> {
            BookInstance bookInstance = detail.getBookInstance();
            Edition edition = bookInstance.getEdition();

            // Update book instance status: BORROWED → AVAILABLE (when return book)
            bookInstance.setStatus(BookStatus.AVAILABLE);
            bookInstanceRepository.save(bookInstance);

            // Increase availableQuantity when changing from BORROWED to AVAILABLE
            edition.setAvailableQuantity(edition.getAvailableQuantity() + 1);
            editionRepository.save(edition); // Save edition to update availableQuantity

            // Set return date
            detail.setRefundDate(LocalDateTime.now());
        });

        // Update borrowing receipt status to RETURNED
        receipt.setStatus(BorrowingStatus.RETURNED);
        borrowingReceiptRepository.save(receipt);

        // TODO: Send email notification to user
        // emailService.sendReturnConfirmationNotification(receipt.getUser().getEmail(), receipt);
    }

    /**
     * Helper method to format book instance information for error messages
     */
    private String formatBookInstanceError(BookInstance bookInstance) {
        return bookInstance.getEdition().getTitle() + " (" + bookInstance.getStatus() + ")";
    }
}
