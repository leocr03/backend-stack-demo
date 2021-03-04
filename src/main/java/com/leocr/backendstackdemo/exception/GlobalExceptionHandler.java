package com.leocr.backendstackdemo.exception;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleInvalidArguments(Exception ex, WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @NotNull
    private ResponseEntity<Object> getExceptionResponseEntity(Exception ex, HttpStatus httpStatus) {
        final Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, Instant.now());
        body.put(STATUS, httpStatus.value());
        body.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, httpStatus);
    }
}
