package com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.repository;

import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmpleadoRepository extends JpaRepository<Empleado, UUID> {
}
