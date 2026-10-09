package com.daiquiriclub.daiquiriclub_ecommerce.modules.usuario.repository;

import com.daiquiriclub.daiquiriclub_ecommerce.modules.usuario.entity.Usuario;
import com.daiquiriclub.daiquiriclub_ecommerce.security.model.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByProviderAndProviderId(AuthProvider authProvider, String providerId);
}
