package com.group2.library_management.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.group2.library_management.entity.BorrowingDetail;
import com.group2.library_management.entity.enums.BookStatus;

import lombok.Builder;

@Builder
public record BorrowingDetailResponse(
        Integer id,
        Integer editionId,
        String title,
        String isbn,
        String barcode,
        BookStatus status,
        BigDecimal acquiredPrice,
        String callNumber,
        LocalDateTime dueDate,
        LocalDateTime actualReturnDate) {
    public static BorrowingDetailResponse fromEntity(BorrowingDetail entity) {
        if (entity == null || entity.getBookInstance() == null || entity.getBookInstance().getEdition() == null) {
            return BorrowingDetailResponse.builder().build();
        }
        return BorrowingDetailResponse.builder()
                .id(entity.getId())
                .editionId(entity.getBookInstance().getEdition().getId())
                .title(entity.getBookInstance().getEdition().getTitle())
                .isbn(entity.getBookInstance().getEdition().getIsbn())
                .barcode(entity.getBookInstance().getBarcode())
                .status(entity.getBookInstance().getStatus())
                .acquiredPrice(entity.getBookInstance().getAcquiredPrice())
                .callNumber(entity.getBookInstance().getCallNumber())
                .dueDate(entity.getDueDate()) // Uses helper method that gets from BorrowingReceipt
                .actualReturnDate(entity.getActualReturnDate()) // Uses helper method that maps refund_date
                .build();
    }
}
