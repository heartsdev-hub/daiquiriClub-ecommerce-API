package com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoriaResponse(
        UUID id,
        String nombre,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
