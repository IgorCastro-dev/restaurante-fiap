package com.fiap.restaurante.services;

import com.fiap.restaurante.domain.dto.LoginDto;
import com.fiap.restaurante.domain.dto.TokenDto;
import com.fiap.restaurante.domain.dto.TrocaSenhaDto;
import com.fiap.restaurante.domain.entity.Usuario;
import com.fiap.restaurante.domain.repository.UsuarioRepository;
import com.fiap.restaurante.domain.services.AuthenticationService;
import com.fiap.restaurante.domain.services.TokenServiceImpl;
import com.fiap.restaurante.exception.CredencialErradoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;


    @Test
    void givenValidCredentials_whenAuthenticate_thenReturnToken() {
        // Arrange
        LoginDto loginDto = new LoginDto("username", "password");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Usuario usuario = new Usuario();
        usuario.setNome("username"); // Certifique-se de que o campo é "username", não "nome"

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(usuario);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication);

        // Cria um mock da interface Token
        Token tokenMock = mock(Token.class);
        when(tokenService.allocateToken(usuario.getUsername())).thenReturn(tokenMock);

        // Act
        Token result = authenticationService.autenticate(loginDto);

        // Assert
        Assertions.assertEquals(tokenMock, result); // Verifica se o token retornado é o mock
        Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(authenticationToken);
        Mockito.verify(tokenService, Mockito.times(1)).allocateToken(usuario.getUsername());
    }

    @Test
    void givenInvalidCredentials_whenAuthenticate_thenThrowCredencialErradoException() {
        // Arrange
        LoginDto loginDto = new LoginDto("username", "wrongpassword");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        when(authenticationManager.authenticate(authenticationToken))
                .thenThrow(new BadCredentialsException("Credenciais inválidas."));

        // Act & Assert
        Assertions.assertThrows(CredencialErradoException.class, () -> authenticationService.autenticate(loginDto));
        Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(authenticationToken);
    }

    @Test
    void givenValidPasswordChange_whenTrocaSenha_thenReturnSuccessMessage() {
        // Arrange
        Date today = new Date();
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .setSubject("username")
                .setIssuer("Token do app")
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime() + 1000 * 60 * 15))
                .compact();
        String bearerToken = String.format("Bearer %s",jwt);
        TrocaSenhaDto trocaSenhaDto = new TrocaSenhaDto("oldPassword", "newPassword", "newPassword");

        Usuario usuario = new Usuario();
        usuario.setLogin("username");
        usuario.setSenha(passwordEncoder.encode("oldPassword"));

        when(usuarioRepository.findByLogin("username")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("oldPassword", usuario.getSenha())).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");


        // Assert
        Assertions.assertEquals("Senha alterada com sucesso", authenticationService.trocaSenha(trocaSenhaDto, bearerToken));
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByLogin("username");
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuario);
    }

    @Test
    void givenMismatchedPasswords_whenTrocaSenha_thenThrowCredencialErradoException() {
        // Arrange
        Date today = new Date();
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .setSubject("username")
                .setIssuer("Token do app")
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime() + 1000 * 60 * 15))
                .compact();
        String bearerToken = String.format("Bearer %s",jwt);
        TrocaSenhaDto trocaSenhaDto = new TrocaSenhaDto("oldPassword", "newPassword", "differentPassword");

        Usuario usuario = new Usuario();
        usuario.setLogin("username");
        usuario.setSenha(passwordEncoder.encode("oldPassword"));

        when(usuarioRepository.findByLogin("username")).thenReturn(Optional.of(usuario));
        // Act & Assert
        Assertions.assertThrows(CredencialErradoException.class, () ->
                authenticationService.trocaSenha(trocaSenhaDto, bearerToken));
    }

    @Test
    void givenIncorrectCurrentPassword_whenTrocaSenha_thenThrowCredencialErradoException() {
        // Arrange
        Date today = new Date();
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .setSubject("username")
                .setIssuer("Token do app")
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime() + 1000 * 60 * 15))
                .compact();
        String bearerToken = String.format("Bearer %s",jwt);
        TrocaSenhaDto trocaSenhaDto = new TrocaSenhaDto("wrongPassword", "newPassword", "newPassword");

        Usuario usuario = new Usuario();
        usuario.setNome("username");
        usuario.setSenha(passwordEncoder.encode("oldPassword"));

        when(usuarioRepository.findByLogin("username")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("wrongPassword", usuario.getSenha())).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(CredencialErradoException.class, () ->
                authenticationService.trocaSenha(trocaSenhaDto, bearerToken));
    }
}