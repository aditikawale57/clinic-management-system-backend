package com.vaishnavishealthcare.common.exception;

public class NotFoundException extends ApiException {

    public NotFoundException() {
        this("Not Found");
    }

    public NotFoundException(String message) {
        super(ErrorType.NOT_FOUND, message);
    }
}
