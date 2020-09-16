package com.service.stajnet.controller.error;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {
    
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss a z", timezone = "GMT+3")
    private LocalDateTime timestamp;
    private List<String> messages;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status){
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex){
        this(status);
        this.messages = Arrays.asList("Unexpected error");
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable ex){
        this(status, Arrays.asList(message), ex);
    }

    public ApiError(HttpStatus status, List<String> messages, Throwable ex){
        this(status);
        this.messages = messages;
        this.debugMessage = ex.getLocalizedMessage();
    }
}