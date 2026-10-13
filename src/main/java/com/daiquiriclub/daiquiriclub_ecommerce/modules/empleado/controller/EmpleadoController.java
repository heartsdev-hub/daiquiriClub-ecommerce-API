package com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.controller;

import com.daiquiriclub.daiquiriclub_ecommerce.common.response.ApiResult;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.dto.request.EmpleadoCreateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.dto.response.EmpleadoResponse;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.service.EmpleadoService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/empleado")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }
    @PostMapping()
    public ResponseEntity<ApiResult<EmpleadoResponse>>createEmpleado(@RequestBody @Valid EmpleadoCreateRequest empleadoCreateRequest) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.createEmpleado(empleadoCreateRequest));
    }
}
