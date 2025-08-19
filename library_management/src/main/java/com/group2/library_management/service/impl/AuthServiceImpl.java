package com.group2.library_management.service.impl;

import com.group2.library_management.dto.request.RegisterRequest;
import com.group2.library_management.dto.request.TokenRefreshRequest;
import com.group2.library_management.dto.response.UserResponse;
import com.group2.library_management.entity.RefreshToken;
import com.group2.library_management.entity.User;
import com.group2.library_management.entity.enums.RoleType;
import com.group2.library_management.entity.enums.UserStatus;
import com.group2.library_management.exception.AlreadyLoggedOutException;
import com.group2.library_management.exception.EmailAlreadyExistsException;
import com.group2.library_management.exception.RefreshTokenExpiredException;
import com.group2.library_management.exception.RefreshTokenNotFoundException;
import com.group2.library_management.mapper.UserMapper;
import com.group2.library_management.repository.RefreshTokenRepository;
import com.group2.library_management.repository.UserRepository;
import com.group2.library_management.security.CustomUserDetails;
import com.group2.library_management.service.AbstractBaseService;
import com.group2.library_management.service.AuthService;
import com.group2.library_management.service.RefreshTokenService;
import com.group2.library_management.service.TokenService;
import com.group2.library_management.dto.request.LoginRequest;
import com.group2.library_management.dto.response.LoginResponse;
import com.group2.library_management.dto.response.TokenRefreshResponse;

import lombok.RequiredArgsConstructor;

import java.time.Instant;

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
public class AuthServiceImpl extends AbstractBaseService implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
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

    @Override
    @Transactional(noRollbackFor = RefreshTokenExpiredException.class)
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.refreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(oldToken -> {
                    if (oldToken.getExpiryDate().compareTo(Instant.now()) < 0) {
                        refreshTokenRepository.delete(oldToken);
                        throw new RefreshTokenExpiredException(); 
                    }

                    User user = oldToken.getUser();
                    CustomUserDetails userDetails = new CustomUserDetails(user);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    String newAccessToken = tokenService.generateToken(authentication);
                    RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(oldToken);

                    return new TokenRefreshResponse(newAccessToken, newRefreshToken.getToken());
                })
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Override
    public void logout() {
        User user = getCurrentUser();
        
        int deletedCount = refreshTokenService.deleteByUser(user);

        // If no record was deleted, it means the user has already logged out
        // or there is no corresponding refresh token in the database to delete.
        if (deletedCount == 0) {
            throw new AlreadyLoggedOutException();
        }
    }
}
