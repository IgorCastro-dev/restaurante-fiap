package com.fiap.restaurante.services;

import com.fiap.restaurante.presentation.dto.RestauranteRequestDto;
import com.fiap.restaurante.presentation.dto.RestauranteResponseDto;
import com.fiap.restaurante.domain.entity.Restaurante;
import com.fiap.restaurante.domain.entity.TipoUsuario;
import com.fiap.restaurante.domain.entity.Usuario;
import com.fiap.restaurante.domain.repository.RestauranteRepository;
import com.fiap.restaurante.application.services.RestauranteService;
import com.fiap.restaurante.application.services.UsuarioService;
import com.fiap.restaurante.application.exception.RestauranteNotFoundException;
import com.fiap.restaurante.application.exception.TipoUsuarioException;
import com.fiap.restaurante.infraestructure.mapper.RestauranteMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestauranteServiceTest {

    @Mock
    private RestauranteMapper restauranteMapper;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private RestauranteService restauranteService;

    @Test
    @Transactional
    void givenValidRestauranteRequestDto_whenSalvarRestaurante_thenReturnRestauranteResponseDto() {
        // Arrange
        RestauranteRequestDto requestDto = new RestauranteRequestDto();
        requestDto.setIdUsuario(1);
        requestDto.setNome("Restaurante Teste");
        requestDto.setEndereco("Endereço Teste");
        requestDto.setTipoCozinha("Cozinha Teste");
        requestDto.setHorarioFuncionamento("Horário Teste");

        Usuario usuario = new Usuario();
        usuario.setIdUsusario(1);
        usuario.setTipoUsuario(TipoUsuario.DONO_DE_RESTAURANTE);

        Restaurante restaurante = new Restaurante();
        restaurante.setIdRestaurante(1L);
        restaurante.setNome("Restaurante Teste");
        restaurante.setDono(usuario);
        restaurante.setEndereco("Endereço Teste");
        restaurante.setTipoCozinha("Cozinha Teste");
        restaurante.setHorarioFuncionamento("Horário Teste");

        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNome("Restaurante Teste");
        responseDto.setEndereco("Endereço Teste");
        responseDto.setTipoCozinha("Cozinha Teste");
        responseDto.setHorarioFuncionamento("Horário Teste");

        when(usuarioService.getUsuarioByid(1)).thenReturn(usuario);
        when(restauranteMapper.requestDtoToEntity(usuario, requestDto)).thenReturn(restaurante);
        when(restauranteRepository.save(restaurante)).thenReturn(restaurante);
        when(restauranteMapper.entityToResponse(restaurante)).thenReturn(responseDto);

        // Act
        RestauranteResponseDto result = restauranteService.salvarRestaurante(requestDto);

        // Assert
        Assertions.assertEquals(responseDto, result);
        Mockito.verify(usuarioService, Mockito.times(1)).getUsuarioByid(1);
        Mockito.verify(restauranteMapper, Mockito.times(1)).requestDtoToEntity(usuario, requestDto);
        Mockito.verify(restauranteRepository, Mockito.times(1)).save(restaurante);
        Mockito.verify(restauranteMapper, Mockito.times(1)).entityToResponse(restaurante);
    }

    @Test
    @Transactional
    void givenInvalidUsuario_whenSalvarRestaurante_thenThrowTipoUsuarioException() {
        // Arrange
        RestauranteRequestDto requestDto = new RestauranteRequestDto();
        requestDto.setIdUsuario(1);

        Usuario usuario = new Usuario();
        usuario.setIdUsusario(1);
        usuario.setTipoUsuario(TipoUsuario.CLIENTE); // Usuário não é Dono de Restaurante

        when(usuarioService.getUsuarioByid(1)).thenReturn(usuario);

        // Act & Assert
        Assertions.assertThrows(TipoUsuarioException.class, () ->
                restauranteService.salvarRestaurante(requestDto));
        Mockito.verify(usuarioService, Mockito.times(1)).getUsuarioByid(1);
    }

    @Test
    void givenRestaurantes_whenListarRestaurante_thenReturnListOfRestauranteResponseDto() {
        // Arrange
        Restaurante restaurante = new Restaurante();
        restaurante.setIdRestaurante(1L);
        restaurante.setNome("Restaurante Teste");

        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNome("Restaurante Teste");

        when(restauranteRepository.findAll()).thenReturn(List.of(restaurante));
        when(restauranteMapper.entitiesToResponseDto(List.of(restaurante))).thenReturn(List.of(responseDto));

        // Act
        List<RestauranteResponseDto> result = restauranteService.listarRestaurante();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(responseDto, result.get(0));
        Mockito.verify(restauranteRepository, Mockito.times(1)).findAll();
        Mockito.verify(restauranteMapper, Mockito.times(1)).entitiesToResponseDto(List.of(restaurante));
    }

    @Test
    void givenValidId_whenDeletaRestaurante_thenDeleteRestaurante() {
        // Arrange
        Restaurante restaurante = new Restaurante();
        restaurante.setIdRestaurante(1L);

        when(restauranteRepository.findById(1)).thenReturn(Optional.of(restaurante));

        // Act
        restauranteService.deletaRestaurante(1);

        // Assert
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(1);
        Mockito.verify(restauranteRepository, Mockito.times(1)).delete(restaurante);
    }

    @Test
    void givenInvalidId_whenDeletaRestaurante_thenThrowRestauranteNotFoundException() {
        // Arrange
        when(restauranteRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(RestauranteNotFoundException.class, () ->
                restauranteService.deletaRestaurante(1));
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void givenValidId_whenGetRestauranteByid_thenReturnRestaurante() {
        // Arrange
        Restaurante restaurante = new Restaurante();
        restaurante.setIdRestaurante(1L);

        when(restauranteRepository.findById(1)).thenReturn(Optional.of(restaurante));

        // Act
        Restaurante result = restauranteService.getRestauranteByid(1);

        // Assert
        Assertions.assertEquals(restaurante, result);
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void givenInvalidId_whenGetRestauranteByid_thenThrowRestauranteNotFoundException() {
        // Arrange
        when(restauranteRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(RestauranteNotFoundException.class, () ->
                restauranteService.getRestauranteByid(1));
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void givenValidRestauranteRequestDto_whenAtualizaRestaurante_thenReturnRestauranteResponseDto() {
        // Arrange
        RestauranteRequestDto requestDto = new RestauranteRequestDto();
        requestDto.setIdUsuario(1);
        requestDto.setNome("Restaurante Atualizado");
        requestDto.setEndereco("Endereço Atualizado");
        requestDto.setTipoCozinha("Cozinha Atualizada");
        requestDto.setHorarioFuncionamento("Horário Atualizado");

        Usuario usuario = new Usuario();
        usuario.setIdUsusario(1);
        usuario.setTipoUsuario(TipoUsuario.DONO_DE_RESTAURANTE);

        Restaurante restaurante = new Restaurante();
        restaurante.setIdRestaurante(1L);
        restaurante.setNome("Restaurante Teste");
        restaurante.setDono(usuario);
        restaurante.setEndereco("Endereço Teste");
        restaurante.setTipoCozinha("Cozinha Teste");
        restaurante.setHorarioFuncionamento("Horário Teste");

        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNome("Restaurante Atualizado");
        responseDto.setEndereco("Endereço Atualizado");
        responseDto.setTipoCozinha("Cozinha Atualizada");
        responseDto.setHorarioFuncionamento("Horário Atualizado");

        when(restauranteRepository.findById(1)).thenReturn(Optional.of(restaurante));
        when(usuarioService.getUsuarioByid(1)).thenReturn(usuario);
        when(restauranteRepository.save(restaurante)).thenReturn(restaurante);
        when(restauranteMapper.entityToResponse(restaurante)).thenReturn(responseDto);

        // Act
        RestauranteResponseDto result = restauranteService.atualizaRestaurante(requestDto, 1);

        // Assert
        Assertions.assertEquals(responseDto, result);
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(1);
        Mockito.verify(usuarioService, Mockito.times(1)).getUsuarioByid(1);
        Mockito.verify(restauranteRepository, Mockito.times(1)).save(restaurante);
        Mockito.verify(restauranteMapper, Mockito.times(1)).entityToResponse(restaurante);
    }

    @Test
    void givenInvalidUsuario_whenAtualizaRestaurante_thenThrowTipoUsuarioException() {
        // Arrange
        RestauranteRequestDto requestDto = new RestauranteRequestDto();
        requestDto.setIdUsuario(1);

        Usuario usuario = new Usuario();
        usuario.setIdUsusario(1);
        usuario.setTipoUsuario(TipoUsuario.CLIENTE); // Usuário não é Dono de Restaurante

        Restaurante restaurante = new Restaurante();
        restaurante.setIdRestaurante(1L);

        when(restauranteRepository.findById(1)).thenReturn(Optional.of(restaurante));
        when(usuarioService.getUsuarioByid(1)).thenReturn(usuario);

        // Act & Assert
        Assertions.assertThrows(TipoUsuarioException.class, () ->
                restauranteService.atualizaRestaurante(requestDto, 1));
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(1);
        Mockito.verify(usuarioService, Mockito.times(1)).getUsuarioByid(1);
    }

    @Test
    void givenValidId_whenGetRestaurante_thenReturnRestauranteResponseDto() {
        // Arrange
        Restaurante restaurante = new Restaurante();
        restaurante.setIdRestaurante(1L);

        RestauranteResponseDto responseDto = new RestauranteResponseDto();

        when(restauranteRepository.findById(1)).thenReturn(Optional.of(restaurante));
        when(restauranteMapper.entityToResponse(restaurante)).thenReturn(responseDto);

        // Act
        RestauranteResponseDto result = restauranteService.getRestaurante(1);

        // Assert
        Assertions.assertEquals(responseDto, result);
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(1);
        Mockito.verify(restauranteMapper, Mockito.times(1)).entityToResponse(restaurante);
    }

    @Test
    void givenInvalidId_whenGetRestaurante_thenThrowRestauranteNotFoundException() {
        // Arrange
        when(restauranteRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(RestauranteNotFoundException.class, () ->
                restauranteService.getRestaurante(1));
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(1);
    }
}
