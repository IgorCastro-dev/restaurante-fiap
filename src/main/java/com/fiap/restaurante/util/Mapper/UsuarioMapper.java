package com.fiap.restaurante.util.Mapper;

import com.fiap.restaurante.domain.dto.UsuarioDto;
import com.fiap.restaurante.domain.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {
    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDto entityToDto(Usuario usuario){
        return modelMapper.map(usuario,UsuarioDto.class);
    }

    public Usuario dtoToEntity(UsuarioDto usuarioDto){
        return modelMapper.map(usuarioDto, Usuario.class);
    }

    public List<UsuarioDto> entitiesToDtos(List<Usuario> usuarios){
        return usuarios.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
