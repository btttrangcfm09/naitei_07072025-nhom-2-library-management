package com.group2.library_management.dto.request.bookinstance;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.*;
import java.time.LocalDate;
import com.group2.library_management.entity.*;
import com.group2.library_management.entity.enums.*;

@Builder

public record CreateBookInstanceDto(@NotNull Edition edition,
                              @NotNull(message = "'Barcode' không được để trống") @Size(max = 100, message = "Độ dài Barcode < 100") String barcode,
                              @NotNull(message = "'Call Number' không được để trống") @Size(max = 100, message = "Độ dài Barcode < 100") String callNumber,
                              @NotNull(message = "'Ngày nhập' không được để trống") LocalDate acquiredDate,
                              @NotNull(message = "'Giá nhập' không được để trông") @Digits(integer = 10, fraction = 2) BigDecimal acquiredPrice,
                              @NotNull BookStatus status,
                              String note) {
                        
}
