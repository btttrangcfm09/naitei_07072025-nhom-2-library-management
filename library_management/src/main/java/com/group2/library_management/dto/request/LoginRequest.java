package com.group2.library_management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "{validation.email.not_blank}")
    @Email(message = "{validation.email.invalid_format}")
    String email,

    @NotBlank(message = "{validation.password.not_blank}")
    String password
) {}
