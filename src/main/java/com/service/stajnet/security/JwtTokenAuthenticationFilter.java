package com.service.stajnet.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component("jwtTokenAuthenticationFilter")
public class JwtTokenAuthenticationFilter extends GenericFilterBean{
    
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenAuthenticationFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        
        String token = getJwtFromCookie((HttpServletRequest)req);
        try{
            if(token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if(auth != null){
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        catch(InvalidJwtAuthenticationException ex){
            // do smth
        }
        filterChain.doFilter(req, res);
    }

    private String getJwtFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null) return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("Authorization")){
                return cookie.getValue();
            }
        }

        return null;
    }
}