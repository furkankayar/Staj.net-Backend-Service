package com.service.stajnet.controller;

import javax.validation.Valid;

import com.service.stajnet.dao.LoginDAO;
import com.service.stajnet.dao.RegisterDAO;
import com.service.stajnet.dto.AuthenticationResponse;
import com.service.stajnet.dto.RegisterationResponse;
import com.service.stajnet.dto.UserDTO;
import com.service.stajnet.service.AuthServiceImpl;
import com.service.stajnet.service.RefreshTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private RefreshTokenService refreshTokenService;
    


    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(
            @CookieValue(name = "Authorization", required = false) String accessToken,
            @CookieValue(name = "Refresh_Token", required = false) String refreshToken,
            @Valid @RequestBody LoginDAO body
    ){
        return authService.login(body, accessToken, refreshToken);
    }

    @PostMapping(path = "/refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshTokens(
        @CookieValue(name = "Authorization", required = false) String accessToken,
        @CookieValue(name = "Refresh_Token", required = true) String refreshToken
    ){
        return authService.refreshToken(refreshToken, accessToken);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Object> logout(
        @CookieValue(name = "Refresh_Token", required = true) String refreshToken
    ){
        refreshTokenService.deleteRefreshToken(refreshToken);
        return authService.logout();
    }
 
    @PostMapping(path = "/register")
    public RegisterationResponse register(@Valid @RequestBody RegisterDAO body){
        
        return authService.register(body);
    }

    @GetMapping(value = "/whoami")
    public UserDTO whoami(
        @CookieValue(name = "Authorization", required = false) String accessToken
    )
    {
        return authService.whoami(accessToken);
    }
}