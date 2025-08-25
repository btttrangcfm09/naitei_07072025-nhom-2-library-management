package com.group2.library_management.dto.response;

import com.group2.library_management.entity.enums.BorrowingStatus;
import java.time.LocalDateTime;
import java.util.List;

public record BorrowingHistoryResponse(
    Integer id,
    BorrowingStatus status,
    LocalDateTime createdAt,
    LocalDateTime borrowedDate,
    LocalDateTime dueDate,
    String rejectedReason,
    List<BorrowedBookSummaryResponse> borrowedBooks
) {}
