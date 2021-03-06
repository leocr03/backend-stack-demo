package com.leocr.backendstackdemo.common.exception;

import com.leocr.backendstackdemo.api.v1.dto.KafkaDto;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Test
    void handleMethodArgumentTypeMismatchException() {
        final GlobalExceptionHandler handler = new GlobalExceptionHandler();
        final String value = "someValue";
        final Class<?> requiredType = MethodArgumentTypeMismatchException.class;
        final String name = "someName";
        final MethodParameter param = mock(MethodParameter.class);
        final Throwable cause = mock(Throwable.class);
        final @NotNull MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(value,
                requiredType, name, param, cause);

        final ResponseEntity<Object> entity = handler.handleMethodArgumentTypeMismatchException(ex);

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                "[someName] should be of type [MethodArgumentTypeMismatchException].",
                "MethodArgumentTypeMismatchException", null);
        apiError.setTimestamp(((ApiError) Objects.requireNonNull(entity.getBody())).getTimestamp());
        apiError.setErrors(((ApiError) Objects.requireNonNull(entity.getBody())).getErrors());
        final ResponseEntity<Object> expected = new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        assertEquals(expected, entity);
    }

    @Test
    void handleConstraintViolationException() {
        final GlobalExceptionHandler handler = new GlobalExceptionHandler();
        final ConstraintViolationException ex = mock(ConstraintViolationException.class);
        final KafkaDto dto = new KafkaDto("someValue", "someMessage");
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<?>> constraintViolations = Collections.unmodifiableSet(validator.validate(dto));
        when(ex.getConstraintViolations()).thenReturn(constraintViolations);

        final ResponseEntity<Object> entity = handler.handleConstraintViolationException(ex);

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, null, null, Collections.singletonList(
                "com.leocr.backendstackdemo.api.v1.dto.KafkaDto value: The value must have equal or less than " +
                        "5 digits"));
        apiError.setTimestamp(((ApiError) Objects.requireNonNull(entity.getBody())).getTimestamp());
        apiError.setType(((ApiError) Objects.requireNonNull(entity.getBody())).getType());
        final ResponseEntity<Object> expected = new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        assertEquals(expected, entity);
    }

    @Test
    void handleAllExceptions() {
        final GlobalExceptionHandler handler = new GlobalExceptionHandler();
        final NullPointerException ex = mock(NullPointerException.class);
        final WebRequest request = mock(WebRequest.class);

        final ResponseEntity<Object> entity = handler.handleAllExceptions(ex, request);

        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, null, null,
                Collections.singletonList("Failed to convert value of type 'java.lang.String' to required type " +
                        "'org.springframework.web.method.annotation.MethodArgumentTypeMismatchException'; " +
                        "nested exception is Mock for Throwable, hashCode: 1689458432"));
        apiError.setTimestamp(((ApiError) Objects.requireNonNull(entity.getBody())).getTimestamp());
        apiError.setType(((ApiError) Objects.requireNonNull(entity.getBody())).getType());
        apiError.setErrors(((ApiError) Objects.requireNonNull(entity.getBody())).getErrors());
        final ResponseEntity<Object> expected = new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(expected, entity);
    }
}