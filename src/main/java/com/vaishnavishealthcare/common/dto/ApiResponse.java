package com.vaishnavishealthcare.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Standard response envelope returned by every endpoint.
 *
 * <p>The {@code status} (HTTP) value lives on the {@link ResponseEntity} and is
 * intentionally kept out of the serialized body, mirroring the sanitisation
 * done by the reference Express {@code ApiResponse}. {@code null} fields (such
 * as {@code data} on message-only responses) are omitted from the JSON.
 *
 * <p>Controllers and the global exception handler build responses through the
 * static factory methods so the shape stays consistent across the API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final String statusCode;
    private final String message;
    private final T data;

    public ApiResponse(StatusCode statusCode, String message, T data) {
        this.statusCode = statusCode.code();
        this.message = message;
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return build(HttpStatus.OK, StatusCode.SUCCESS, message, data);
    }

    public static ResponseEntity<ApiResponse<Void>> success(String message) {
        return build(HttpStatus.OK, StatusCode.SUCCESS, message, null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        return build(HttpStatus.CREATED, StatusCode.SUCCESS, message, data);
    }

    public static ResponseEntity<ApiResponse<Void>> failure(
            HttpStatus status, StatusCode statusCode, String message) {
        return build(status, statusCode, message, null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> build(
            HttpStatus status, StatusCode statusCode, String message, T data) {
        return ResponseEntity.status(status)
                .body(new ApiResponse<>(statusCode, message, data));
    }
}
