package com.vaishnavishealthcare.common.exception;

public class BadTokenException extends ApiException {

    public BadTokenException() {
        this("Bad Token");
    }

    public BadTokenException(String message) {
        super(ErrorType.BAD_TOKEN, message);
    }
}
