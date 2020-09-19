package com.service.stajnet.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NotAllowedException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public NotAllowedException() {
        super("You are not allowed for this method!");
    }
}