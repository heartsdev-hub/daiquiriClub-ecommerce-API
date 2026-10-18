package com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.mapper;

import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.dto.request.EmpleadoCreateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.dto.response.EmpleadoResponse;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.entity.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "fotoPerfilKey", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Empleado toEmpleado(EmpleadoCreateRequest empleadoCreateRequest);

    @Mapping(target = "email", source = "empleado.usuario.email")
    @Mapping(target = "authProvider", source = "empleado.usuario.authProvider")
    @Mapping(target = "providerId", source = "empleado.usuario.providerId")
    @Mapping(target = "roles", source = "empleado.usuario.roles")
    @Mapping(target = "fotoPerfilUrl", source = "fotoPerfilUrl")
    EmpleadoResponse toEmpleadoResponse(Empleado empleado, String fotoPerfilUrl);
}
