package com.vaishnavishealthcare.common.dto;

/**
 * Application-level status codes that help API consumers understand the
 * outcome of a request independently of the HTTP status. Mirrors the
 * {@code StatusCode} enum from the reference Express service.
 */
public enum StatusCode {

    SUCCESS("10000"),
    FAILURE("10001"),
    RETRY("10002"),
    INVALID_ACCESS_TOKEN("10003");

    private final String code;

    StatusCode(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
