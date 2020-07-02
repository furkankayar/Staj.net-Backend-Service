package com.service.stajnet.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import com.service.stajnet.model.RefreshToken;
import com.service.stajnet.repository.IRefreshTokenRepository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@Component("refreshTokenService")
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
    
    private final IRefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token){
        refreshTokenRepository.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}