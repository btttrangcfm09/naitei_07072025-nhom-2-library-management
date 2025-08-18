package com.group2.library_management.service;

import com.group2.library_management.dto.request.LoginRequest;
import com.group2.library_management.dto.request.RegisterRequest;
import com.group2.library_management.dto.request.TokenRefreshRequest;
import com.group2.library_management.dto.response.LoginResponse;
import com.group2.library_management.dto.response.TokenRefreshResponse;
import com.group2.library_management.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    TokenRefreshResponse refreshToken(TokenRefreshRequest request);
}
