package com.daiquiriclub.daiquiriclub_ecommerce.common.response;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String path
) {
}
