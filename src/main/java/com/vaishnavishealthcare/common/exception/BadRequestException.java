package com.vaishnavishealthcare.common.exception;

public class BadRequestException extends ApiException {

    public BadRequestException() {
        this("Bad Request");
    }

    public BadRequestException(String message) {
        super(ErrorType.BAD_REQUEST, message);
    }
}
