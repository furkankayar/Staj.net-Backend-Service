package com.service.stajnet.security;

import javax.naming.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
    
    private static final long serialVersionUID = 1L;

    public InvalidJwtAuthenticationException(String e){
        super(e);
    }
}