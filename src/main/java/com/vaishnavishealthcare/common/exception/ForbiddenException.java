package com.vaishnavishealthcare.common.exception;

public class ForbiddenException extends ApiException {

    public ForbiddenException() {
        this("Permission denied");
    }

    public ForbiddenException(String message) {
        super(ErrorType.FORBIDDEN, message);
    }
}
