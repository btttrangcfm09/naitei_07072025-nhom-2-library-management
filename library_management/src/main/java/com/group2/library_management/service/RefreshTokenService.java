package com.group2.library_management.service;

import com.group2.library_management.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createNewRefreshToken(Integer userId);
} 
