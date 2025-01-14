package com.fiap.restaurante.domain.entity;

import org.springframework.security.core.GrantedAuthority;

public enum TipoUsuario implements GrantedAuthority {
    CLIENTE,
    RESTAURANTE;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
