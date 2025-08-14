package com.group2.library_management.controller.api;

import com.group2.library_management.dto.request.RegisterRequest;
import com.group2.library_management.dto.response.BaseApiResponse;
import com.group2.library_management.dto.response.UserResponse;
import com.group2.library_management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse registeredUser = authService.register(request);

        BaseApiResponse<UserResponse> response = new BaseApiResponse<>(
            HttpStatus.CREATED.value(),
            registeredUser,
            "Đăng ký tài khoản thành công"
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
