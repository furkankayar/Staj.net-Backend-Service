package com.service.stajnet.security;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;

import com.service.stajnet.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component("jwtTokenProvider")
public class JwtTokenProvider {
    

    @Autowired
    private UserServiceImpl userService;

    @Value("${jwt.expiration.time}")
    private long jwtExpirationInMillis;

    @Value("${jwt.private.token}")
    private String privateToken;

    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(privateToken.getBytes());
    }

    public String createToken(String username, Collection<? extends GrantedAuthority> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtExpirationInMillis);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String getUsername(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); 
        }
        catch(ExpiredJwtException e){
            return e.getClaims().getSubject();
        }
    }


    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) throws InvalidJwtAuthenticationException {

        try{
             
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if(claims.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        }
        catch(JwtException | IllegalArgumentException e){
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }
}