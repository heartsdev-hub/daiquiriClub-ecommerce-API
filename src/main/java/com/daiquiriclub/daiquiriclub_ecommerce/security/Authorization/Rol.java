package com.daiquiriclub.daiquiriclub_ecommerce.security.Authorization;

import java.util.Set;

public enum Rol {
    ADMINISTRADOR(Set.of(

            Permission.PRODUCT_READ,
            Permission.PRODUCT_READ_ACTIVE,
            Permission.PRODUCT_CREATE,
            Permission.PRODUCT_UPDATE,
            Permission.PRODUCT_DELETE,

            Permission.CATEGORIA_READ,
            Permission.CATEGORIA_READ_ACTIVE,
            Permission.CATEGORIA_CREATE,
            Permission.CATEGORIA_UPDATE,
            Permission.CATEGORIA_DELETE
    )),
    VENDEDOR(Set.of()),
    CLIENTE(Set.of()),
    REPARTIDOR(Set.of());

    private final Set<Permission> permissions;

    Rol(Set<Permission> permissions) {
        this.permissions = permissions;
    }
    public Set<Permission> getPermissions(){
        return permissions;
    }
}
