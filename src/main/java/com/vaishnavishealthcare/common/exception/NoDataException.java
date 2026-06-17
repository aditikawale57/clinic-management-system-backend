package com.vaishnavishealthcare.common.exception;

public class NoDataException extends ApiException {

    public NoDataException() {
        this("No data available");
    }

    public NoDataException(String message) {
        super(ErrorType.NO_DATA, message);
    }
}
