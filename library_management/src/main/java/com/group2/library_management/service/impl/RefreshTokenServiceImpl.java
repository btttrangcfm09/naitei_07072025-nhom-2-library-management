package com.group2.library_management.service.impl;

import com.group2.library_management.entity.RefreshToken;
import com.group2.library_management.entity.User;
import com.group2.library_management.exception.UserNotFoundException;
import com.group2.library_management.exception.RefreshTokenExpiredException;
import com.group2.library_management.repository.RefreshTokenRepository;
import com.group2.library_management.repository.UserRepository;
import com.group2.library_management.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${application.security.jwt.refresh-token.expiration-ms}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository; 

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    @Override
    public RefreshToken createNewRefreshToken(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.flush();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .token(UUID.randomUUID().toString())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Phương thức dùng để cập nhật token và ngày hết hạn mới cho bản ghi RefreshToken đã có.
     * @param token Đối tượng RefreshToken cũ
     * @return Đối tượng RefreshToken đã được cập nhật và lưu
     */
    @Transactional
    @Override
    public RefreshToken rotateRefreshToken(RefreshToken token) {
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        return refreshTokenRepository.save(token);
    }
}
