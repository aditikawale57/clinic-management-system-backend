package com.vaishnavishealthcare.common.exception;

public class NoEntryException extends ApiException {

    public NoEntryException() {
        this("Entry doesn't exist");
    }

    public NoEntryException(String message) {
        super(ErrorType.NO_ENTRY, message);
    }
}
