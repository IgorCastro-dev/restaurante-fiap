package com.fiap.restaurante.application.services;

import com.fiap.restaurante.domain.entity.TipoUsuario;
import com.fiap.restaurante.domain.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoUsuarioService {
    @Autowired
    UsuarioService usuarioService;

    public List<String> listar() {
        return Arrays.stream(TipoUsuario.values())
                .map(TipoUsuario::getDescricao)
                .collect(Collectors.toList());
    }


    @Transactional
    public void associaTipoUsuario(Integer idUsuario, String tipoUsuario) {
        Usuario usuario = usuarioService.getUsuarioByid(idUsuario);
        TipoUsuario tipo = TipoUsuario.fromDescricao(tipoUsuario);
        usuario.setTipoUsuario(tipo);
    }

}
