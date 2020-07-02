package com.service.stajnet;

import com.service.stajnet.controller.error.ApiError;

import org.springframework.http.ResponseEntity;

public class Utility {
    
    public ResponseEntity<Object> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}