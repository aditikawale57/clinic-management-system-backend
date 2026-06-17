package com.vaishnavishealthcare.common.exception;

public class InternalException extends ApiException {

    public InternalException() {
        this("Internal error");
    }

    public InternalException(String message) {
        super(ErrorType.INTERNAL, message);
    }
}
