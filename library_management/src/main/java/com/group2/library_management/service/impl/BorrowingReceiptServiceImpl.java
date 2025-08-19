package com.group2.library_management.service.impl;

import com.group2.library_management.dto.response.BorrowingReceiptResponse;
import com.group2.library_management.entity.BorrowingReceipt;
import com.group2.library_management.entity.User;
import com.group2.library_management.entity.enums.BorrowingStatus;
import com.group2.library_management.repository.BorrowingReceiptRepository;
import com.group2.library_management.service.BorrowingReceiptService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingReceiptServiceImpl implements BorrowingReceiptService {

    private final BorrowingReceiptRepository borrowingReceiptRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<BorrowingReceiptResponse> getAllBorrowingRequests(String keyword,
                                                                  BorrowingStatus status,
                                                                  Pageable pageable) {
        Specification<BorrowingReceipt> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("user");
            }

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (StringUtils.hasText(keyword)) {
                Join<BorrowingReceipt, User> userJoin = root.join("user");
                String pattern = "%" + keyword.toLowerCase().trim() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(userJoin.get("name")), pattern),
                        cb.like(cb.lower(userJoin.get("email")), pattern)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<BorrowingReceipt> receiptPage = borrowingReceiptRepository.findAll(spec, pageable);
        return receiptPage.map(BorrowingReceiptResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public BorrowingReceiptResponse getBorrowingRequestById(Integer id) {
        BorrowingReceipt receipt = borrowingReceiptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy phiếu mượn với ID:" +
                        " " + id));

        return BorrowingReceiptResponse.fromEntity(receipt, true);
    }
}
