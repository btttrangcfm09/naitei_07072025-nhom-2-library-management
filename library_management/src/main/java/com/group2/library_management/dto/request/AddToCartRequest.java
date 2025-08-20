package com.group2.library_management.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddToCartRequest(
    @NotNull(message = "{validation.id.not_null}")
    @Positive(message = "{validation.id.positive}")
    Integer editionId,

    @NotNull(message = "{validation.quantity.not_null}")
    @Min(value = 1, message = "{validation.quantity.min}")
    @Max(value = 2, message = "{validation.quantity.max}")
    Integer quantity
) {}
