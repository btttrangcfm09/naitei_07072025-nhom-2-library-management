package com.group2.library_management.dto.request.bookinstance;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.*;
import java.time.LocalDate;
import com.group2.library_management.entity.*;
import com.group2.library_management.entity.enums.*;

@Builder

public record CreateBookInstanceDto(@NotNull Edition edition,
                              @NotNull(message = "{bookinstance.barcode.notBlank}") @Size(max = 100, message = "{bookinstance.barcode.size}") String barcode,
                              @NotNull(message = "{bookinstance.callNumber.notBlank}") @Size(max = 100, message = "{bookinstance.callNumber.size}") String callNumber,
                              @NotNull(message = "{bookinstance.acquiredDate.notBlank}") 
                              @PastOrPresent(message = "{bookinstance.acquiredDate.pastOrPresent}") LocalDate acquiredDate,
                              @NotNull(message = "{bookinstance.acquiredPrice.notBlank}") @Digits(integer = 10, fraction = 2) BigDecimal acquiredPrice,
                              @NotNull BookStatus status,
                              String note) {
                        
}
