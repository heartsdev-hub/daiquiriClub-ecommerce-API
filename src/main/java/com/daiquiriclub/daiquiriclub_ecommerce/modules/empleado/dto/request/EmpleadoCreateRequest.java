package com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.dto.request;

import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoDocumento;
import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoSexo;
import com.daiquiriclub.daiquiriclub_ecommerce.security.Authorization.Rol;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

public record EmpleadoCreateRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        @NotBlank(message = "El apellido es obligatorio")
        String apellidos,
        @NotNull(message = "El tipo de documento es obligatorio")
        TipoDocumento tipoDocumento,
        @NotBlank(message = "El numero de documento es obligatorio")
        String numDocumento,
        @NotNull(message = "El sexo es obligatorio")
        TipoSexo tipoSexo,
        @Size(max = 20,message = "El celular no puede superar 20 caracteres")
        String celular,
        @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
        LocalDate fechaNacimiento,
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Debe ser en formato email")
        String email,
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 100,
                message = "La contraseña debe tener entre 8 y 100 caracteres")
        String password,
        @NotEmpty(message = "Debe asignar al menos un rol")
        Set<@NotNull(message = "El rol no puede ser nulo") Rol> roles
) {
}
