package com.service.stajnet.service;

import java.time.Instant;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.service.stajnet.controller.exception.UserNotFoundException;
import com.service.stajnet.controller.mapper.InheritMapper;
import com.service.stajnet.dao.LoginDAO;
import com.service.stajnet.dao.RegisterDAO;
import com.service.stajnet.dto.AuthenticationResponse;
import com.service.stajnet.dto.RegisterationResponse;
import com.service.stajnet.dto.UserDTO;
import com.service.stajnet.model.User;
import com.service.stajnet.repository.IRoleRepository;
import com.service.stajnet.security.InvalidJwtAuthenticationException;
import com.service.stajnet.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
    private IRoleRepository roleRepository;

    @Autowired
    private InheritMapper mapper;

    @Value("${jwt.expiration.time}")
    private long jwtExpirationInMillis;

    @Value("${jwt.token.name}")
    private String jwtTokenName;

    @Value("${refresh.token.name}")
    private String refreshTokenName;

    @Override
    public ResponseEntity<AuthenticationResponse> login(LoginDAO loginDAO, String accessToken, String refreshToken) {
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDAO.getUsername(), loginDAO.getPassword()));

        boolean accessTokenValid = false;
        boolean refreshTokenValid = false;
        try{
            accessTokenValid = jwtTokenProvider.validateToken(accessToken);
        }
        catch(InvalidJwtAuthenticationException ex){
            accessTokenValid = false;
        }

        try{
            refreshTokenValid = refreshTokenService.validateRefreshToken(refreshToken);
        }
        catch(IllegalArgumentException ex){
            refreshTokenValid = false;
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        String newAccessToken = accessToken;
        String newRefreshToken = refreshToken;
        
        if((!accessTokenValid && !refreshTokenValid)){
            newAccessToken = jwtTokenProvider.createToken(loginDAO.getUsername(), this.userService.findByUsername(loginDAO.getUsername()).orElseThrow(
                    () -> new UsernameNotFoundException("Username " + loginDAO.getUsername() + " not found"))
                    .getAuthorities());
            newRefreshToken = refreshTokenService.generateRefreshToken().getToken();
            responseHeaders.add(HttpHeaders.SET_COOKIE, cookieGenerator(jwtTokenName, newAccessToken).toString());
            responseHeaders.add(HttpHeaders.SET_COOKIE, cookieGenerator(refreshTokenName, newRefreshToken).toString());
        }
        else if(!accessTokenValid && refreshTokenValid){
            newAccessToken = jwtTokenProvider.createToken(loginDAO.getUsername(), this.userService.findByUsername(loginDAO.getUsername()).orElseThrow(
                    () -> new UsernameNotFoundException("Username " + loginDAO.getUsername() + " not found"))
                    .getAuthorities());
            responseHeaders.add(HttpHeaders.SET_COOKIE, cookieGenerator(jwtTokenName, newAccessToken).toString());
        }

        return ResponseEntity.ok()
                             .headers(responseHeaders)
                             .body(
                                AuthenticationResponse.builder()
                                    .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                                    .username(loginDAO.getUsername())
                                    .build()
                            );
    }

    @Override
    public ResponseEntity<AuthenticationResponse> refreshToken(String refreshToken, String accessToken) {
        
        refreshTokenService.validateRefreshToken(refreshToken);
       
        User user = userService.findByUsername(jwtTokenProvider.getUsername(accessToken))
            .orElseThrow(() -> new UserNotFoundException(jwtTokenProvider.getUsername(accessToken)));
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getAuthorities());
       
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieGenerator(jwtTokenName, token).toString());

        return ResponseEntity.ok()
                             .headers(responseHeaders)
                             .body(
                                AuthenticationResponse.builder()
                                    .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                                    .username(user.getUsername())
                                    .build()
                            );
    }

    @Override
    public RegisterationResponse register(RegisterDAO registerDAO){

        registerDAO.setPassword(passwordEncoder.encode(registerDAO.getPassword()));
        User newUser = mapper.registerDAOToUserEntity(registerDAO);
        newUser.getRoles().add(roleRepository.findByRole("user"));
        userService.save(newUser);
 
        return RegisterationResponse.builder().status(HttpStatus.OK).message("Registration successful!").build();
    }

    @Override
    public UserDTO whoami(String accessToken){
        return mapper.userEntityToDTO(
                    userService.findByUsername(jwtTokenProvider.getUsername(accessToken))
                               .orElseThrow(() -> new UserNotFoundException(jwtTokenProvider.getUsername(accessToken)))
        );
    }

    @Override
    public ResponseEntity<Object> logout(){

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieGenerator(jwtTokenName, "", 0).toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieGenerator(refreshTokenName, "", 0).toString());

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(responseHeaders)
                             .body(Stream.of(new AbstractMap.SimpleEntry<>("message", "Logged out successfully!"))
                             .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private ResponseCookie cookieGenerator(String name, String token){
        return ResponseCookie.from(name, token)
                            .maxAge(Integer.MAX_VALUE)
                            .httpOnly(true)
                            .path("/")
                            .secure(false)
                            .build();
    } 

    private ResponseCookie cookieGenerator(String name, String token, int age){
        return ResponseCookie.from(name, token)
                            .maxAge(age)
                            .httpOnly(true)
                            .path("/")
                            .secure(false)
                            .build();
    } 
}