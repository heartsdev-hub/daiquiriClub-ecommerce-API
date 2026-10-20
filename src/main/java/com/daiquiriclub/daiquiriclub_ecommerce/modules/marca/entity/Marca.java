package com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.entity;

import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.PaisOrigen;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Marca")
@EntityListeners(AuditingEntityListener.class)
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String nombre;
    private String slug;
    private String logoUrl;
    @Enumerated(EnumType.STRING)
    private PaisOrigen paisOrigen;
    private boolean active = true;
    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}