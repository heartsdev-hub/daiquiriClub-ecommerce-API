package com.daiquiriclub.daiquiriclub_ecommerce.common.response;

public record ApiResult <T>(
        boolean success,
        String message,
        T data
) {
}
