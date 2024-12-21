package com.fiap.restaurante.domain.services;

import com.fiap.restaurante.domain.dto.UsuarioSemSenhaDto;
import com.fiap.restaurante.domain.repository.UsuarioRepository;
import com.fiap.restaurante.domain.dto.UsuarioDto;
import com.fiap.restaurante.domain.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fiap.restaurante.util.Mapper.UsuarioMapper;

import java.time.LocalDateTime;
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

    public UsuarioSemSenhaDto salvarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioMapper.dtoToEntity(usuarioDto);
        boolean usuarioJaExiste = usuarioRepository
                .findByEmailOrLogin(usuarioDto.getEmail(),usuarioDto.getLogin())
                .isPresent();
        if(usuarioJaExiste){
            throw new IllegalArgumentException("Usuário com este e-mail ou login já existe.");
        }
        return usuarioMapper.entityToSemSenhaDto(usuarioRepository.save(usuario));
    }

    public UsuarioDto getUsuario(Integer idUsuario) {
        return usuarioMapper.entityToDto(getUsuarioByid(idUsuario));
    }


    public void deletaUsuario(Integer idUsuario) {
        usuarioRepository.delete(getUsuarioByid(idUsuario));
    }

    public UsuarioSemSenhaDto atualizaUsuario(UsuarioSemSenhaDto usuarioDto, Integer idUsuario) {
        Usuario usuarioExistente = getUsuarioByid(idUsuario);

        usuarioExistente.setNome(usuarioDto.getNome());
        usuarioExistente.setEmail(usuarioDto.getEmail());
        usuarioExistente.setEndereco(usuarioDto.getEndereco());
        usuarioExistente.setDataAlteracao(LocalDateTime.now());

        return usuarioMapper.entityToSemSenhaDto(usuarioRepository.save(usuarioExistente));
    }

    private Usuario getUsuarioByid(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario).orElseThrow(() ->
                new RuntimeException("Usuário com ID " + idUsuario + " não encontrado"));
    }
}
