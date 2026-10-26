package com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.dto.response;

import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.PaisOrigen;

import java.time.LocalDateTime;
import java.util.UUID;

public record MarcaResponse(
        UUID id,
        String nombre,
        String slug,
        String logoUrl,
        PaisOrigen paisOrigen,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
