package com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.entity;

import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoDocumento;
import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoSexo;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.usuario.entity.Usuario;
import com.daiquiriclub.daiquiriclub_ecommerce.security.Authorization.Permission;
import com.daiquiriclub.daiquiriclub_ecommerce.security.Authorization.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Empleado")
@EntityListeners(AuditingEntityListener.class)
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;
    @Column(nullable = false,unique = true)
    private String numDocumento;
    @Enumerated(EnumType.STRING)
    private TipoSexo tipoSexo;
    private String celular;
    private LocalDate fechaNacimiento;
    @Column(name = "foto_perfil_key", length = 500)
    private String fotoPerfilKey;
    private boolean active = true;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "usuario_id",
            nullable = false,
            unique = true
    )
    private Usuario usuario;
    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
