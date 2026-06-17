package com.vaishnavishealthcare.common.exception;

/**
 * Categories of domain errors. Each type is mapped to an HTTP status and an
 * application {@link com.vaishnavishealthcare.common.dto.StatusCode} by the
 * {@link GlobalExceptionHandler}.
 */
public enum ErrorType {

    BAD_TOKEN,
    TOKEN_EXPIRED,
    UNAUTHORIZED,
    ACCESS_TOKEN,
    INTERNAL,
    NOT_FOUND,
    NO_ENTRY,
    NO_DATA,
    BAD_REQUEST,
    FORBIDDEN
}
