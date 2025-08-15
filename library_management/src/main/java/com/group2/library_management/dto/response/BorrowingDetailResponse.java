package com.group2.library_management.dto.response;

import com.group2.library_management.entity.BorrowingDetail;
import com.group2.library_management.entity.enums.BookStatus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BorrowingDetailResponse(
        Integer editionId,
        String title,
        String isbn,
        String barcode,
        BookStatus status,
        BigDecimal acquiredPrice,
        String callNumber
) {
    public static BorrowingDetailResponse fromEntity(BorrowingDetail entity) {
        if (entity == null || entity.getBookInstance() == null || entity.getBookInstance().getEdition() == null) {
            return BorrowingDetailResponse.builder().build();
        }
        return BorrowingDetailResponse.builder()
                .editionId(entity.getBookInstance().getEdition().getId())
                .title(entity.getBookInstance().getEdition().getTitle())
                .isbn(entity.getBookInstance().getEdition().getIsbn())
                .barcode(entity.getBookInstance().getBarcode())
                .status(entity.getBookInstance().getStatus())
                .acquiredPrice(entity.getBookInstance().getAcquiredPrice())
                .callNumber(entity.getBookInstance().getCallNumber())
                .build();
    }
}
