package com.fiap.restaurante.domain.entity;

import com.fiap.restaurante.exception.TipoUsuarioNotFoundException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

public enum TipoUsuario implements GrantedAuthority {
    CLIENTE("Cliente"),
    DONO_DE_RESTAURANTE("Dono de Restaurante");

    private final String descricao;

    TipoUsuario(String descricao) {
        this.descricao = descricao;
    }

    public static TipoUsuario fromDescricao(String descricao) {
        return Arrays.stream(TipoUsuario.values())
                .filter(tipo -> tipo.getDescricao().equalsIgnoreCase(descricao))
                .findFirst()
                .orElseThrow(() -> new TipoUsuarioNotFoundException("Tipo de usuário não encontrado: " + descricao));
    }

    @Override
    public String getAuthority() {
        return this.name();
    }

    public String getDescricao() {
        return descricao;
    }
}
