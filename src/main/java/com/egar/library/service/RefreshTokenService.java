package com.egar.library.service;

import com.egar.library.entity.RefreshToken;
import com.egar.library.repos.RefreshTokenRepository;
import com.egar.library.exceptions.RefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${app.jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;
    private final RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshToken> findRefreshToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId){
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration.toMillis()))
                .token(UUID.randomUUID().toString())
                .build();
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken checkRefreshToken(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException(refreshToken.getToken(), "Refresh token was expired. Repeat sign in action");

        }
            return refreshToken;
    }

    public void deleteByUserId(Long userId){
        refreshTokenRepository.deleteByUserId(userId);
    }
}
