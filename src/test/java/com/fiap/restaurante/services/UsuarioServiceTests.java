package com.fiap.restaurante.services;

import com.fiap.restaurante.domain.dto.UsuarioDto;
import com.fiap.restaurante.domain.dto.UsuarioSemSenhaDto;
import com.fiap.restaurante.domain.entity.Usuario;
import com.fiap.restaurante.domain.repository.UsuarioRepository;
import com.fiap.restaurante.domain.services.UsuarioService;
import com.fiap.restaurante.exception.CredencialErradoException;
import com.fiap.restaurante.exception.UsuarioAlreadyExistsException;
import com.fiap.restaurante.exception.UsuarioNotFoundException;
import com.fiap.restaurante.util.mapper.UsuarioMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTests {
    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    private UsuarioDto usuarioDto;

    private UsuarioSemSenhaDto usuarioSemSenhaDto;

    @BeforeEach
    public void setup() {
        Usuario usuario = new Usuario();
        usuario.setIdUsusario(1);
        usuario.setEmail("teste@example.com");
        usuario.setLogin("teste");
        this.usuario = usuario;

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail("teste@.com");
        usuarioDto.setLogin("teste");
        usuarioDto.setSenha("Teste@123");
        this.usuarioDto = usuarioDto;

        UsuarioSemSenhaDto usuarioSemSenhaDto = new UsuarioSemSenhaDto();
        usuarioSemSenhaDto.setEmail("teste@example.com");
        usuarioSemSenhaDto.setLogin("teste");
        this.usuarioSemSenhaDto = usuarioSemSenhaDto;
    }

    @Test
    void give_sucess_when_idUsuarioIsPresent_then_returnUsuario() {
        Mockito.when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        Assertions.assertEquals(usuario, usuarioService.getUsuarioByid(1));
        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void given_failure_when_idUsuarioIsNotPresent_then_throwUsuarioNotFoundException() {
        Mockito.when(usuarioRepository.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(UsuarioNotFoundException.class, () -> usuarioService.getUsuarioByid(2));
        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(2);
    }

    @Test
    void given_created_when_usuarioDtoIsPresent_then_returnUsuarioSemSenhaDto() {
        Mockito.when(usuarioMapper.dtoToEntity(usuarioDto))
                .thenReturn(usuario);

        Mockito.when(usuarioRepository.findByEmailOrLogin("teste@.com", "teste"))
                .thenReturn(Optional.empty());

        Mockito.when(passwordEncoder.encode(Mockito.anyString()))
                .thenReturn("senhaCodificada");

        Mockito.when(usuarioMapper.entityToSemSenhaDto(usuario))
                .thenReturn(usuarioSemSenhaDto);

        Mockito.when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        Assertions.assertEquals(usuarioSemSenhaDto, usuarioService.salvarUsuario(usuarioDto));

        Mockito.verify(usuarioRepository, Mockito.times(1))
                .findByEmailOrLogin("teste@.com", "teste");

        Mockito.verify(usuarioRepository, Mockito.times(1))
                .save(usuario);
    }

    @Test
    void given_listaUsuario_when_thereAreUsers_then_returnListOfUsuarioDto() {
        List<Usuario> usuarios = List.of(usuario);
        List<UsuarioDto> usuarioDtos = List.of(usuarioDto);

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
        Mockito.when(usuarioMapper.entitiesToDtos(usuarios)).thenReturn(usuarioDtos);

        Assertions.assertEquals(usuarioDtos, usuarioService.listaUsuario());

        Mockito.verify(usuarioRepository, Mockito.times(1)).findAll();
        Mockito.verify(usuarioMapper, Mockito.times(1)).entitiesToDtos(usuarios);
    }

    @Test
    void given_getUsuario_when_idUsuarioIsPresent_then_returnUsuarioDto() {
        Mockito.when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        Mockito.when(usuarioMapper.entityToDto(usuario)).thenReturn(usuarioDto);

        Assertions.assertEquals(usuarioDto, usuarioService.getUsuario(1));

        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(1);
        Mockito.verify(usuarioMapper, Mockito.times(1)).entityToDto(usuario);
    }

    @Test
    void given_getUsuario_when_idUsuarioIsNotPresent_then_throwUsuarioNotFoundException() {
        Mockito.when(usuarioRepository.findById(2)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsuarioNotFoundException.class, () -> usuarioService.getUsuario(2));

        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(2);
    }

    @Test
    void given_deletaUsuario_when_idUsuarioIsPresent_then_deleteUsuario() {
        Mockito.when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        usuarioService.deletaUsuario(1);

        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(1);
        Mockito.verify(usuarioRepository, Mockito.times(1)).delete(usuario);
    }

    @Test
    void given_deletaUsuario_when_idUsuarioIsNotPresent_then_throwUsuarioNotFoundException() {
        Mockito.when(usuarioRepository.findById(2)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsuarioNotFoundException.class, () -> usuarioService.deletaUsuario(2));

        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(2);
    }

    @Test
    void given_atualizaUsuario_when_idUsuarioIsPresent_then_returnUsuarioSemSenhaDto() {
        Mockito.when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Mockito.when(usuarioMapper.entityToSemSenhaDto(usuario)).thenReturn(usuarioSemSenhaDto);

        Assertions.assertEquals(usuarioSemSenhaDto, usuarioService.atualizaUsuario(usuarioSemSenhaDto, 1));

        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(1);
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuario);
        Mockito.verify(usuarioMapper, Mockito.times(1)).entityToSemSenhaDto(usuario);
    }



    @Test
    void given_atualizaUsuario_when_idUsuarioIsNotPresent_then_throwUsuarioNotFoundException() {
        Mockito.when(usuarioRepository.findById(2)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsuarioNotFoundException.class, () -> usuarioService.atualizaUsuario(usuarioSemSenhaDto, 2));

        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(2);
    }

    @Test
    void given_loadUserByUsername_when_loginIsPresent_then_returnUserDetails() {
        Mockito.when(usuarioRepository.findByLogin("teste")).thenReturn(Optional.of(usuario));

        Assertions.assertEquals(usuario, usuarioService.loadUserByUsername("teste"));

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByLogin("teste");
    }

    @Test
    void given_loadUserByUsername_when_loginIsNotPresent_then_throwCredencialErradoException() {
        Mockito.when(usuarioRepository.findByLogin("teste2")).thenReturn(Optional.empty());

        Assertions.assertThrows(CredencialErradoException.class, () -> usuarioService.loadUserByUsername("teste2"));

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByLogin("teste2");
    }
}
