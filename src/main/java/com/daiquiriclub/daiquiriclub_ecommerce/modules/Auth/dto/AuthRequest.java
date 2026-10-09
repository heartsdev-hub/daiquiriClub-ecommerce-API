package com.daiquiriclub.daiquiriclub_ecommerce.modules.Auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Debe ser en formato email")
        String email,
        @NotBlank(message = "El password es obligatorio")
        String password
) {
}
