package com.daiquiriclub.daiquiriclub_ecommerce.modules.usuario.entity;

import com.daiquiriclub.daiquiriclub_ecommerce.security.model.AuthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Usuario")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,unique = true)
    private String email;
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;
    private String providerId;
    private boolean active;
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
