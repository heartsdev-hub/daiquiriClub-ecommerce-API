package com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.dto.response;

import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoDocumento;
import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoSexo;
import com.daiquiriclub.daiquiriclub_ecommerce.security.Authorization.Rol;
import com.daiquiriclub.daiquiriclub_ecommerce.security.model.AuthProvider;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record EmpleadoResponse(
    UUID id,
    String nombre,
    String apellidos,
    TipoDocumento tipoDocumento,
    String numDocumento,
    TipoSexo tipoSexo,
    String celular,
    LocalDate fechaNacimiento,
    String fotoPerfilUrl,
    boolean active,
    String email,
    AuthProvider authProvider,
    String providerId,
    Set<Rol> roles,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
