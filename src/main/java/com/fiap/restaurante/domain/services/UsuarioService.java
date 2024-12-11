package com.fiap.restaurante.domain.services;

import com.fiap.restaurante.domain.repository.UsuarioRepository;
import com.fiap.restaurante.domain.dto.UsuarioDto;
import com.fiap.restaurante.domain.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fiap.restaurante.util.Mapper.UsuarioMapper;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;
    public List<UsuarioDto> listaUsuario() {
        return usuarioMapper.entitiesToDtos(usuarioRepository.findAll());
    }

    public UsuarioDto salvarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioMapper.dtoToEntity(usuarioDto);
        boolean usuarioJaExiste = usuarioRepository
                .findByEmailOrLogin(usuarioDto.getEmail(),usuarioDto.getLogin())
                .isPresent();
        if(usuarioJaExiste){
            throw new IllegalArgumentException("Usuário com este e-mail ou login já existe.");
        }
        usuarioRepository.save(usuario);
        return usuarioDto;
    }
}
