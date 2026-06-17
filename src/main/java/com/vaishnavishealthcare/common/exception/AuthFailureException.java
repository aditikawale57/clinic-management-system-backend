package com.vaishnavishealthcare.common.exception;

public class AuthFailureException extends ApiException {

    public AuthFailureException() {
        this("Invalid Credentials");
    }

    public AuthFailureException(String message) {
        super(ErrorType.UNAUTHORIZED, message);
    }
}
