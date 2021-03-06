package com.leocr.backendstackdemo.common.exception;

import lombok.Data;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Data
public class ApiError {

    private final HttpStatus status;

    private final String message;

    @Setter
    private String type;

    @Setter
    private Instant timestamp;

    @Setter
    private List<String> errors;

    public ApiError(HttpStatus status, String message, String type, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.type = type;
        this.timestamp = Instant.now();
    }
}