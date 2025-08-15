package com.group2.library_management.service;

import java.util.Optional;

import com.group2.library_management.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createNewRefreshToken(Integer userId);

    Optional<RefreshToken> findByToken(String token);

    RefreshToken rotateRefreshToken(RefreshToken token);
} 
