package com.group2.library_management.service;

import com.group2.library_management.dto.request.BorrowingHistoryRequestParams;
import com.group2.library_management.dto.response.BorrowingHistoryResponse;
import org.springframework.data.domain.Page;

public interface BorrowingHistoryService {
    Page<BorrowingHistoryResponse> getMyBorrowingHistory(BorrowingHistoryRequestParams params);
}
