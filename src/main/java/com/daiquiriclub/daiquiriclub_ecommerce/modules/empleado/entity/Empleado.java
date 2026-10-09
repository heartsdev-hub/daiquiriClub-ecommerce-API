package com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.entity;

import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoDocumento;
import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoSexo;
import com.daiquiriclub.daiquiriclub_ecommerce.security.Authorization.Rol;
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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    private String nombres;
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
    private boolean active = true;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "empleado_rol",
            joinColumns = @JoinColumn(name = "empleado_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Set<Rol> roles = new HashSet<>();
    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
