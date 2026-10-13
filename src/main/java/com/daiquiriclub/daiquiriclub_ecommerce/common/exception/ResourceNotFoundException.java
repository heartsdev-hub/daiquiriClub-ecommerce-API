package com.daiquiriclub.daiquiriclub_ecommerce.common.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
