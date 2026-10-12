package com.daiquiriclub.daiquiriclub_ecommerce.common.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
