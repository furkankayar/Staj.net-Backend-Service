package com.service.stajnet.service;

import java.time.Instant;

import com.service.stajnet.controller.exception.UserNotFoundException;
import com.service.stajnet.controller.mapper.InheritMapper;
import com.service.stajnet.dao.LoginDAO;
import com.service.stajnet.dao.RefreshTokenDAO;
import com.service.stajnet.dao.RegisterDAO;
import com.service.stajnet.dto.AuthenticationResponse;
import com.service.stajnet.dto.RegisterationResponse;
import com.service.stajnet.model.User;
import com.service.stajnet.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("authService")
public class AuthServiceImpl implements IAuthService{
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InheritMapper mapper;

    @Value("${jwt.expiration.time}")
    private long jwtExpirationInMillis;

    @Override
    public AuthenticationResponse login(LoginDAO loginDAO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDAO.getUsername(), loginDAO.getPassword()));
        String token = jwtTokenProvider.createToken(loginDAO.getUsername(),
                this.userService.findByUsername(loginDAO.getUsername()).orElseThrow(
                        () -> new UsernameNotFoundException("Username " + loginDAO.getUsername() + " not found"))
                        .getAuthorities());

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis)).username(loginDAO.getUsername()).build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenDAO refreshTokenDAO) {
        refreshTokenService.validateRefreshToken(refreshTokenDAO.getRefreshToken());
        User user = userService.findByUsername(refreshTokenDAO.getUsername())
            .orElseThrow(() -> new UserNotFoundException(refreshTokenDAO.getUsername()));
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getAuthorities());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenDAO.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                .username(user.getUsername())
                .build();
    }

    @Override
    public RegisterationResponse register(RegisterDAO registerDAO){

        registerDAO.setPassword(passwordEncoder.encode(registerDAO.getPassword()));
        userService.save(mapper.registerDAOToUserEntity(registerDAO));

        return RegisterationResponse.builder().status(HttpStatus.OK).message("Registration successful!").build();
    }
}