package com.group2.library_management.service.impl;

import com.group2.library_management.dto.request.RegisterRequest;
import com.group2.library_management.dto.response.UserResponse;
import com.group2.library_management.entity.RefreshToken;
import com.group2.library_management.entity.User;
import com.group2.library_management.entity.enums.RoleType;
import com.group2.library_management.entity.enums.UserStatus;
import com.group2.library_management.exception.EmailAlreadyExistsException;
import com.group2.library_management.mapper.UserMapper;
import com.group2.library_management.repository.UserRepository;
import com.group2.library_management.security.CustomUserDetails;
import com.group2.library_management.service.AuthService;
import com.group2.library_management.service.RefreshTokenService;
import com.group2.library_management.service.TokenService;
import com.group2.library_management.dto.request.LoginRequest;
import com.group2.library_management.dto.response.LoginResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse register(RegisterRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new EmailAlreadyExistsException(request.email());
        });

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(RoleType.CLIENT);
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);

        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (user.getRole() != RoleType.CLIENT) {
            throw new AccessDeniedException("");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenService.generateToken(authentication);
        RefreshToken refreshToken = refreshTokenService.createNewRefreshToken(user.getId());

        return new LoginResponse(accessToken, refreshToken.getToken());
    }
}
