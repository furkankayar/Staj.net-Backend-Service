package com.service.stajnet.service;

import com.service.stajnet.dao.LoginDAO;
import com.service.stajnet.dao.RefreshTokenDAO;
import com.service.stajnet.dto.AuthenticationResponse;

public interface IAuthService {
    
    public AuthenticationResponse login(LoginDAO loginDAO);
    public AuthenticationResponse refreshToken(RefreshTokenDAO refreshTokenDAO);
}