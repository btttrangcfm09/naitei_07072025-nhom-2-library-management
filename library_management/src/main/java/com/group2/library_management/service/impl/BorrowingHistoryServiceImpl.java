package com.group2.library_management.service.impl;

import com.group2.library_management.dto.request.BorrowingHistoryRequestParams;
import com.group2.library_management.dto.response.BorrowingHistoryResponse;
import com.group2.library_management.mapper.BorrowingHistoryMapper;
import com.group2.library_management.entity.BorrowingReceipt;
import com.group2.library_management.entity.User;
import com.group2.library_management.repository.BorrowingHistoryRepository;
import com.group2.library_management.repository.specification.BorrowingReceiptSpecification;
import com.group2.library_management.service.AbstractBaseService;
import com.group2.library_management.service.BorrowingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BorrowingHistoryServiceImpl extends AbstractBaseService implements BorrowingHistoryService {

    private final BorrowingHistoryRepository borrowingHistoryRepository;
    private final BorrowingHistoryMapper borrowingHistoryMapper;

    private static final List<String> ALLOWED_SORT_FIELDS = Arrays.asList("createdAt", "borrowedDate", "dueDate", "status");

    @Override
    public Page<BorrowingHistoryResponse> getMyBorrowingHistory(BorrowingHistoryRequestParams params) {
        User currentUser = getCurrentUser();
        return getBorrowingHistoryForUser(params, currentUser);
    }
    
    // A more generic private method to enhance reusability for different user roles in the future
    private Page<BorrowingHistoryResponse> getBorrowingHistoryForUser(BorrowingHistoryRequestParams params, User user) {
        // Validate and sanitize pagination and sorting inputs
        int page = Math.max(0, Optional.ofNullable(params.page()).orElse(0));
        int size = Math.max(1, Optional.ofNullable(params.size()).orElse(20));
        
        String sortDir = Optional.ofNullable(params.dir())
                                 .filter(dir -> dir.equalsIgnoreCase("asc"))
                                 .orElse("desc");
                                 
        String sortField = Optional.ofNullable(params.sort())
                                   .filter(ALLOWED_SORT_FIELDS::contains)
                                   .orElse("createdAt");

        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        // Use the new Specification Builder for a clean and flexible query construction
        Specification<BorrowingReceipt> spec = new BorrowingReceiptSpecification.Builder()
                .withUser(user)
                .withStatuses(params.statuses())
                .withFromDate(params.fromDate())
                .withToDate(params.toDate())
                .build();

        Page<BorrowingReceipt> receiptPage = borrowingHistoryRepository.findAll(spec, pageable);

        return receiptPage.map(borrowingHistoryMapper::toBorrowingHistoryResponse);
    }
}
