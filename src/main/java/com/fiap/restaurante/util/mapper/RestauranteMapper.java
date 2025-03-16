package com.fiap.restaurante.util.mapper;

import com.fiap.restaurante.domain.dto.RestauranteRequestDto;
import com.fiap.restaurante.domain.dto.RestauranteResponseDto;
import com.fiap.restaurante.domain.entity.Restaurante;
import com.fiap.restaurante.domain.entity.Usuario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteMapper {
    @Autowired
    UsuarioMapper usuarioMapper;

    @Autowired
    private ModelMapper modelMapper;
    public Restaurante requestDtoToEntity(Usuario usuario,RestauranteRequestDto restauranteRequestDto){
        return Restaurante.builder()
                .dono(usuario)
                .nome(restauranteRequestDto.getNome())
                .endereco(restauranteRequestDto.getEndereco())
                .horarioFuncionamento(restauranteRequestDto.getHorarioFuncionamento())
                .tipoCozinha(restauranteRequestDto.getTipoCozinha())
                .build();

    }

    public RestauranteResponseDto entityToResponse(Restaurante restaurante){
        RestauranteResponseDto restauranteResponseDto = modelMapper.map(restaurante,RestauranteResponseDto.class);
        restauranteResponseDto.setUsuarioDto(usuarioMapper.entityToSemSenhaDto(restaurante.getDono()));
        return restauranteResponseDto;
    }
}