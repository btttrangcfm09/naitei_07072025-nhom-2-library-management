package com.group2.library_management.dto.response;

import com.group2.library_management.entity.BorrowingReceipt;
import com.group2.library_management.entity.enums.BorrowingStatus;

import java.time.LocalDateTime;

public record BorrowingReceiptResponse(
        Integer id,
        String borrowerName,
        String borrowerEmail,
        LocalDateTime requestDate,
        LocalDateTime borrowedDate,
        LocalDateTime dueDate,
        BorrowingStatus status
) {
    public static BorrowingReceiptResponse fromEntity(BorrowingReceipt entity) {
        return new BorrowingReceiptResponse(
                entity.getId(),
                entity.getUser().getName(),
                entity.getUser().getEmail(),
                entity.getCreatedAt(),
                entity.getBorrowedDate(),
                entity.getDueDate(),
                entity.getStatus()
        );
    }
}
