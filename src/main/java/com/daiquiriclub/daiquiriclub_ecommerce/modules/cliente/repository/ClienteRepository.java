package com.daiquiriclub.daiquiriclub_ecommerce.modules.cliente.repository;

import com.daiquiriclub.daiquiriclub_ecommerce.modules.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    boolean existsByUsuarioId(UUID usuarioId);
}
