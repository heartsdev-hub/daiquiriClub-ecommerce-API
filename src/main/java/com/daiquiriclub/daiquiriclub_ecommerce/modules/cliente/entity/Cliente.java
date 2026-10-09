package com.daiquiriclub.daiquiriclub_ecommerce.modules.cliente.entity;

import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoDocumento;
import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoSexo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

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
    private String codigo;
    private String nombre;
    private String apellido;
    private TipoDocumento tipoDocumento;
    private LocalDate fechaNacimiento;
    private Integer edad;
    private TipoSexo tipoSexo;
    private String numCelular;
    private String segundoNumCelular;
    private String fotoPerfil;

}
