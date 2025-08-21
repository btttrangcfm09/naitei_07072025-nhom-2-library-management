package com.group2.library_management.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.group2.library_management.entity.enums.BookStatus;
import lombok.*;

@Builder
public record BookInstanceDetailResponse(Integer id,
                                        String bookTitle,
                                        String editionTitle,
                                        String coverImageUrl,
                                        String isbn,
                                        String barcode,
                                        String callNumber,
                                        LocalDate acquiredDate,
                                        BigDecimal acquiredPrice,
                                        BookStatus status,
                                        String note ) {
    
}
