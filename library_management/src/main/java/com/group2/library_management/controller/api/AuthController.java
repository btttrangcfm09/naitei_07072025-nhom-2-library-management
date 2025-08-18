package com.group2.library_management.controller.api;

import com.group2.library_management.dto.request.RegisterRequest;
import com.group2.library_management.dto.request.TokenRefreshRequest;
import com.group2.library_management.dto.response.BaseApiResponse;
import com.group2.library_management.dto.response.UserResponse;
import com.group2.library_management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group2.library_management.dto.request.LoginRequest;
import com.group2.library_management.dto.response.LoginResponse;
import com.group2.library_management.dto.response.TokenRefreshResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MessageSource messageSource;

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    @PostMapping("/register")
    public ResponseEntity<BaseApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse registeredUser = authService.register(request);

        String successMessage = getMessage("success.register");

        BaseApiResponse<UserResponse> response = new BaseApiResponse<>(
                HttpStatus.CREATED.value(),
                registeredUser,
                successMessage);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);

        String successMessage = getMessage("success.login");

        BaseApiResponse<LoginResponse> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                loginResponse,
                successMessage);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<BaseApiResponse<TokenRefreshResponse>> refreshToken(
            @Valid @RequestBody TokenRefreshRequest request) {
        TokenRefreshResponse refreshResponse = authService.refreshToken(request);

        String successMessage = getMessage("success.token.refresh");

        BaseApiResponse<TokenRefreshResponse> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                refreshResponse,
                successMessage);

        return ResponseEntity.ok(response);
    }
}
