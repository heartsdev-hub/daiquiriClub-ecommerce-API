package com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.dto.request;

import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.PaisOrigen;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record MarcaCreateRequest(
        @NotBlank(message = "El nombre es obligatorio.")
        String nombre,
        @NotBlank(message = "El slug es obligatorio.")
        String slug,
        @NotNull(message = "El pais de origen es obligatorio")
        PaisOrigen paisOrigen,
        MultipartFile logo
) {
}
