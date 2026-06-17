package com.vaishnavishealthcare.common.exception;

/**
 * Base class for all domain exceptions. Carries an {@link ErrorType} that the
 * {@link GlobalExceptionHandler} uses to derive the HTTP response. Mirrors the
 * {@code ApiError} hierarchy of the reference Express service.
 */
public class ApiException extends RuntimeException {

    private final transient ErrorType type;

    public ApiException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }
}
