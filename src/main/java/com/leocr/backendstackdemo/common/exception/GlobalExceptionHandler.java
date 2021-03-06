package com.leocr.backendstackdemo.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public @NotNull ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            @NotNull MethodArgumentTypeMismatchException ex) {
        final String name = ex.getName();
        final String type = Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        final String message = String.format("[%s] should be of type [%s].", name, type);
        final List<String> errors = new ArrayList<>() {{
            add(ex.getMessage());
        }};
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message, ex.getClass().getSimpleName(), errors);
        return getExceptionResponseEntity(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public @NotNull ResponseEntity<Object> handleConstraintViolationException(
            @NotNull ConstraintViolationException ex) {
        final List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
                    + violation.getMessage());
        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
                ex.getClass().getSimpleName(), errors);
        return getExceptionResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public @NotNull ResponseEntity<Object> handleAllExceptions(@NotNull Exception ex, WebRequest request) {
        final List<String> errors = new ArrayList<>();
        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
                ex.getClass().getSimpleName(), errors);
        return getExceptionResponseEntity(apiError);
    }

    private @NotNull ResponseEntity<Object> getExceptionResponseEntity(@NotNull ApiError apiError) {
        final HttpStatus httpStatus = apiError.getStatus();
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
