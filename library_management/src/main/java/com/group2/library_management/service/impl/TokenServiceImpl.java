package com.group2.library_management.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.group2.library_management.service.TokenService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    private final JwtEncoder encoder;

    public TokenServiceImpl(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    @Value("${application.security.jwt.expiration-minutes}")
    private long expirationMinutes;

    @Override
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self") // Người cấp phát token
                .issuedAt(now) // Thời gian cấp phát
                .expiresAt(now.plus(expirationMinutes, ChronoUnit.MINUTES)) // Thời gian hết hạn 
                .subject(authentication.getName()) // Chủ thể
                .claim("scope", scope) // Thêm claim chứa các quyền
                .build();
        
        // Mã hóa và ký token
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
