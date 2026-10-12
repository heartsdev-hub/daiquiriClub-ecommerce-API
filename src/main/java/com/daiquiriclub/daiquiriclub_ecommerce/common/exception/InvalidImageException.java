package com.daiquiriclub.daiquiriclub_ecommerce.common.exception;

public class InvalidImageException extends RuntimeException{
    public InvalidImageException(String message) {
        super(message);
    }

    public InvalidImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
