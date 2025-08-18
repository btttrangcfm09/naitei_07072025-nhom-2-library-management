package com.group2.library_management.dto.response;

public record LoginResponse(
    String accessToken,
    String refreshToken
) {}
