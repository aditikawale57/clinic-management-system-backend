package com.vaishnavishealthcare.common.exception;

public class AccessTokenException extends ApiException {

    public AccessTokenException() {
        this("Invalid access token");
    }

    public AccessTokenException(String message) {
        super(ErrorType.ACCESS_TOKEN, message);
    }
}
