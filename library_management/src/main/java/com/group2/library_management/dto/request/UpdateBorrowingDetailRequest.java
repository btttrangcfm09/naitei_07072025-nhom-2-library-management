package com.group2.library_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateBorrowingDetailRequest {

    @NotNull(message = "{admin.borrowing.validation.borrowingDetailId.notNull}")
    private Integer borrowingDetailId;

    @NotBlank(message = "{admin.borrowing.validation.action.notBlank}")
    private String action; // BORROWED, NOT_BORROWED, RETURN, EXTEND, LOST, DAMAGED

    // For RETURN action
    private LocalDateTime actualReturnDate;

    // For EXTEND action
    @Positive(message = "{admin.borrowing.validation.extendDays.positive}")
    private Integer extendDays;
}
