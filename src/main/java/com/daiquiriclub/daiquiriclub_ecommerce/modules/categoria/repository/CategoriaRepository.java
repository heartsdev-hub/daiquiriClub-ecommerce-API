package com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.repository;

import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    @Query(
            """
            SELECT c FROM Categoria c WHERE c.active = true
            """
    )
    List<Categoria> obtenerCategoriasActivas();
    boolean existsByNombre(String nombre);
}
