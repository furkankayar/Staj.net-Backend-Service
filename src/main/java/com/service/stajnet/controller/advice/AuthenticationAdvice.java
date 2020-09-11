package com.service.stajnet.controller.advice;

import com.service.stajnet.Utility;
import com.service.stajnet.controller.error.ApiError;
import com.service.stajnet.security.InvalidJwtAuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthenticationAdvice {
    
    @Autowired
    private Utility utility;

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        String error = "Missing arguments!";
        return utility.buildErrorResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        String error = "Request body has to be in JSON format!";
        return utility.buildErrorResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex){
        String error = "Invalid username or password!";
        return utility.buildErrorResponseEntity(new ApiError(HttpStatus.FORBIDDEN, error, ex));
    }

    @ExceptionHandler(value = InvalidJwtAuthenticationException.class)
    public ResponseEntity<Object> handleInvalidJwtAuthenticationExceptipon(InvalidJwtAuthenticationException ex){
        String error = "Check your authorization token!";
        return utility.buildErrorResponseEntity(new ApiError(HttpStatus.FORBIDDEN, error, ex));
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        String error = "Username and email must be unique!";
        return utility.buildErrorResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex){
        String error = "Invalid argument!";
        return utility.buildErrorResponseEntity(new ApiError(HttpStatus.FORBIDDEN, error, ex));
    }
}