package com.vaishnavishealthcare.common.exception;

public class TokenExpiredException extends ApiException {

    public TokenExpiredException() {
        this("Token has expired");
    }

    public TokenExpiredException(String message) {
        super(ErrorType.TOKEN_EXPIRED, message);
    }
}
