package com.service.stajnet.controller;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import com.service.stajnet.dao.LoginDAO;
import com.service.stajnet.dao.RefreshTokenDAO;
import com.service.stajnet.service.AuthServiceImpl;
import com.service.stajnet.service.RefreshTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {
    
    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @CrossOrigin("http://localhost:8000")
    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDAO body){
        return ResponseEntity.ok(authService.login(body));
    }

    @PostMapping(path = "/refresh/token")
    public ResponseEntity<Object> refreshTokens(@Valid @RequestBody RefreshTokenDAO body){
        return ResponseEntity.ok(authService.refreshToken(body));
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Object> logout(@Valid @RequestBody RefreshTokenDAO body){
        refreshTokenService.deleteRefreshToken(body.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(Stream.of(
            new AbstractMap.SimpleEntry<>("message", "Refresh token deleted successfully!"))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
}