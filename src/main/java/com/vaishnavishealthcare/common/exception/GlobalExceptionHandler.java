package com.vaishnavishealthcare.common.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vaishnavishealthcare.config.AppProperties;
import com.vaishnavishealthcare.common.dto.ApiResponse;
import com.vaishnavishealthcare.common.dto.StatusCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Central translation of exceptions into the standard {@link ApiResponse}
 * envelope. This is the Spring equivalent of the reference service's
 * {@code ApiError.handle} switch and the {@code validator} error formatting.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final AppProperties properties;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException ex) {
        return switch (ex.getType()) {
            case BAD_TOKEN, TOKEN_EXPIRED, UNAUTHORIZED ->
                    ApiResponse.failure(HttpStatus.UNAUTHORIZED, StatusCode.FAILURE, ex.getMessage());
            case ACCESS_TOKEN ->
                    ApiResponse.failure(HttpStatus.UNAUTHORIZED, StatusCode.INVALID_ACCESS_TOKEN, ex.getMessage());
            case NOT_FOUND, NO_ENTRY, NO_DATA ->
                    ApiResponse.failure(HttpStatus.NOT_FOUND, StatusCode.FAILURE, ex.getMessage());
            case BAD_REQUEST ->
                    ApiResponse.failure(HttpStatus.BAD_REQUEST, StatusCode.FAILURE, ex.getMessage());
            case FORBIDDEN ->
                    ApiResponse.failure(HttpStatus.FORBIDDEN, StatusCode.FAILURE, ex.getMessage());
            case INTERNAL ->
                    ApiResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, StatusCode.FAILURE, ex.getMessage());
        };
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining(","));
        return ApiResponse.failure(HttpStatus.BAD_REQUEST, StatusCode.FAILURE, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception ex) {
        log.error("Unhandled exception", ex);
        // Do not leak internal details in production.
        String message = properties.isProduction() ? "Something wrong happened." : ex.getMessage();
        return ApiResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, StatusCode.FAILURE, message);
    }

    private String formatFieldError(FieldError error) {
        return error.getField() + " " + error.getDefaultMessage();
    }
}
