package com.fiap.restaurante.domain.services;


import com.fiap.restaurante.domain.dto.RestauranteRequestDto;
import com.fiap.restaurante.domain.dto.RestauranteResponseDto;
import com.fiap.restaurante.domain.entity.Restaurante;
import com.fiap.restaurante.domain.entity.TipoUsuario;
import com.fiap.restaurante.domain.entity.Usuario;
import com.fiap.restaurante.domain.repository.RestauranteRepository;
import com.fiap.restaurante.exception.RestauranteNotFoundException;
import com.fiap.restaurante.exception.TipoUsuarioException;
import com.fiap.restaurante.exception.UsuarioNotFoundException;
import com.fiap.restaurante.util.mapper.RestauranteMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteService {
    private static final String RESTAURANTE_NOT_FOUND_MESSAGE = "Restaurante com o id: %d não encontrado";
    @Autowired
    RestauranteMapper restauranteMapper;
    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    UsuarioService usuarioService;

    @Transactional
    public RestauranteResponseDto salvarRestaurante(RestauranteRequestDto restauranteRequestDto) {
        Usuario usuario = usuarioService.getUsuarioByid(restauranteRequestDto.getIdUsuario());

        if(!usuario.getTipoUsuario().equals(TipoUsuario.DONO_DE_RESTAURANTE)){
            throw new TipoUsuarioException("Este usuário não é um "+TipoUsuario.DONO_DE_RESTAURANTE.getDescricao());
        }
        Restaurante restaurante = restauranteMapper.requestDtoToEntity(usuario,restauranteRequestDto);
        restauranteRepository.save(restaurante);
        return restauranteMapper.entityToResponse(restaurante);
    }

    public List<RestauranteResponseDto> listarRestaurante() {
        return restauranteMapper.entitiesToResponseDto(restauranteRepository.findAll());
    }

    public void deletaRestaurante(Integer idRestaurante) {
        restauranteRepository.delete(getRestauranteByid(idRestaurante));
    }

    public Restaurante getRestauranteByid(Integer idRestaurante) {
        return restauranteRepository.findById(idRestaurante).orElseThrow(
                ()-> new RestauranteNotFoundException(String.format(RESTAURANTE_NOT_FOUND_MESSAGE,idRestaurante))
        );
    }
}
