package com.fiap.restaurante.filter;

import com.fiap.restaurante.domain.entity.TipoUsuario;
import com.fiap.restaurante.domain.entity.Usuario;
import com.fiap.restaurante.domain.repository.UsuarioRepository;
import com.fiap.restaurante.application.exception.UsuarioNotFoundException;
import com.fiap.restaurante.infraestructure.filter.AutenticateFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticateFilterTest {
    @InjectMocks
    private AutenticateFilter autenticateFilter;

    @Mock
    private TokenService tokenService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Token mockToken;

    @Test
    void testGetToken_NoToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        autenticateFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testGetToken_ValidToken() throws ServletException, IOException {
        Date today = new Date();
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,
                        "$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .setSubject("username")
                .setIssuer("Token do app")
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime() + 1000 * 60 * 15))
                .compact();

        Usuario usuario = new Usuario();
        usuario.setTipoUsuario(TipoUsuario.CLIENTE);

        when(request.getHeader("Authorization")).thenReturn(String.format("Bearer %s", jwt));
        when(tokenService.verifyToken(anyString())).thenReturn(mockToken);
        when(mockToken.getExtendedInformation()).thenReturn("user123");
        when(usuarioRepository.findByLogin("user123")).thenReturn(Optional.of(usuario));

        autenticateFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(usuario, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain).doFilter(request, response);
    }



    @Test
    void testGetToken_InvalidToken() throws ServletException, IOException {

        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(tokenService.verifyToken("invalid-token")).thenReturn(mockToken);
        when(mockToken.getExtendedInformation()).thenReturn("user123");
        when(usuarioRepository.findByLogin("user123")).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> autenticateFilter.doFilterInternal(request, response, filterChain));
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, never()).doFilter(request, response);
    }
}
