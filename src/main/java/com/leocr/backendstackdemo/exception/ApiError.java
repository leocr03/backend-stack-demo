package com.leocr.backendstackdemo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Data
public class ApiError {

    private final HttpStatus status;

    private final String message;

    private String type;

    private Instant timestamp;

    private final List<String> errors;

    public ApiError(HttpStatus status, String message, String type, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.type = type;
        this.timestamp = Instant.now();
    }
}