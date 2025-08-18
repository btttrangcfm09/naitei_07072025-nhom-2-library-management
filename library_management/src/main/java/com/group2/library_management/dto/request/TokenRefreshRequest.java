package com.group2.library_management.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(
    @NotBlank(message = "{validation.refreshtoken.not_blank}")
    String refreshToken
) {}
