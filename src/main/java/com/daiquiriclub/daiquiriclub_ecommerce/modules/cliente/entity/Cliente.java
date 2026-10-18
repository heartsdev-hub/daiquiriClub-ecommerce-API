package com.daiquiriclub.daiquiriclub_ecommerce.modules.cliente.entity;

import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoDocumento;
import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoSexo;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Cliente")
@EntityListeners(AuditingEntityListener.class)
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;
    private String apellidos;
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;
    @Column(unique = true)
    private String numDocumento;
    @Enumerated(EnumType.STRING)
    private TipoSexo tipoSexo;
    private LocalDate fechaNacimiento;
    private Integer edad;
    private String numCelular;
    private String segundoNumCelular;
    @Column(name = "foto_perfil_key",length = 500)
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
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
