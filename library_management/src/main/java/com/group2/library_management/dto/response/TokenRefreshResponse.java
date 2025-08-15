package com.group2.library_management.dto.response;

public record TokenRefreshResponse(
    String accessToken,
    String refreshToken
) {} 
