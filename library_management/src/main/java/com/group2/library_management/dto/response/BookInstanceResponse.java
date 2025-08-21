package com.group2.library_management.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.group2.library_management.entity.enums.BookStatus;

import lombok.*;

@Builder
public record BookInstanceResponse(String id,
                                   String barcode,
                                   String callNumber,
                                   LocalDate acquiredDate,
                                   BigDecimal acquiredPrice,
                                   BookStatus status) {

}
