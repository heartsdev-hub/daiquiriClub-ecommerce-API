package com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.repository;

import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MarcaRepository extends JpaRepository<Marca, UUID> {
    boolean existsByNombre(String nombre);
    boolean existsBySlug(String slug);
    Optional<Marca> findBySlug (String slug);
    List<Marca> findByActiveTrue();
}
