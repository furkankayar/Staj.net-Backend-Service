package com.service.stajnet.service;

import com.service.stajnet.dao.LoginDAO;
import com.service.stajnet.dao.RegisterDAO;
import com.service.stajnet.dto.AuthenticationResponse;
import com.service.stajnet.dto.RegisterationResponse;
import com.service.stajnet.security.InvalidJwtAuthenticationException;

import org.springframework.http.ResponseEntity;

public interface IAuthService {
    
    public ResponseEntity<AuthenticationResponse> login(LoginDAO loginDAO, String accessToken, String refreshToken) throws InvalidJwtAuthenticationException;
    public ResponseEntity<AuthenticationResponse> refreshToken(String refreshToken, String accessToken);
    public RegisterationResponse register(RegisterDAO registerDAO);
}