package com.service.stajnet.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotProperIdException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public NotProperIdException(Object id) {
        super("Requested id is not acceptable: " + id);
    }
}