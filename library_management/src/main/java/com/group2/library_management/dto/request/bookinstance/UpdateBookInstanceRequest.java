package com.group2.library_management.dto.request.bookinstance;

import jakarta.validation.constraints.*;
import java.math.*;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import com.group2.library_management.entity.enums.*;

public record UpdateBookInstanceRequest(
                              @NotBlank(message = "{bookinstance.barcode.notBlank}") @Size(max = 100, message = "{bookinstance.barcode.size}") String barcode,
                              @NotBlank(message = "{bookinstance.callNumber.notBlank}") @Size(max = 100, message = "{bookinstance.callNumber.size}") String callNumber,
                              @NotNull(message = "{bookinstance.acquiredDate.notBlank}") 
                              @PastOrPresent(message = "{bookinstance.acquiredDate.pastOrPresent}")
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate acquiredDate,
                              @NotNull(message = "{bookinstance.acquiredPrice.notBlank}") @Digits(integer = 10, fraction = 2) BigDecimal acquiredPrice,
                              @NotNull(message = "{bookinstance.status.notBlank}") BookStatus status,
                              String note) {
    
}
