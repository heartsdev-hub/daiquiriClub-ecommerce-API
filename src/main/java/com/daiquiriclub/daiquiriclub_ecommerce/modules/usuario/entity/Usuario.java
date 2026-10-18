package com.daiquiriclub.daiquiriclub_ecommerce.modules.usuario.entity;

import com.daiquiriclub.daiquiriclub_ecommerce.security.Authorization.Permission;
import com.daiquiriclub.daiquiriclub_ecommerce.security.Authorization.Rol;
import com.daiquiriclub.daiquiriclub_ecommerce.security.model.AuthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Usuario")
@EntityListeners(AuditingEntityListener.class)
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,unique = true)
    private String email;
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;
    @Column(unique = true)
    private String providerId;
    private boolean active;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(
                    name = "usuario_id"
            )
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Set<SimpleGrantedAuthority> authorities = new HashSet<>();
       for(Rol rol : roles){
           authorities.add(new SimpleGrantedAuthority("ROLE_"+rol.name()));
           for(Permission permission : rol.getPermissions()){
               authorities.add(new SimpleGrantedAuthority(permission.name()));
           }
       }
       return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
