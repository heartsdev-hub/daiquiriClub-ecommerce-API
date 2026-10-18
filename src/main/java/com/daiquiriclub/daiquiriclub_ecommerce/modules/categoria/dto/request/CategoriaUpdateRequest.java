package com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoriaUpdateRequest(
        @NotBlank(message = "La categoria es obligatoria.")
        String nombre
) {
}
