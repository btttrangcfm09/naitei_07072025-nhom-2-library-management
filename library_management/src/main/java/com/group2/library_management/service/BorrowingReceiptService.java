package com.group2.library_management.service;

import com.group2.library_management.dto.response.BorrowingReceiptResponse;
import com.group2.library_management.entity.enums.BorrowingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BorrowingReceiptService {
    Page<BorrowingReceiptResponse> getAllBorrowingRequests(String keyword, BorrowingStatus status
            , Pageable pageable);

    BorrowingReceiptResponse getBorrowingRequestById(Integer id);
}
