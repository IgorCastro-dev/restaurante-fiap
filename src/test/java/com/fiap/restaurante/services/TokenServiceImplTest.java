package com.fiap.restaurante.services;

import com.fiap.restaurante.domain.dto.TokenDto;
import com.fiap.restaurante.domain.services.TokenServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Date;

import static org.mockito.Mockito.*;

public class TokenServiceImplTest {

    @Test
    void givenValidUsername_whenAllocateToken_thenReturnValidTokenDto() {
        // Arrange
        String username = "username";
        TokenServiceImpl tokenService = new TokenServiceImpl();

        // Act
        TokenDto result = (TokenDto) tokenService.allocateToken(username);

        // Assert
        Assertions.assertNotNull(result); // Verifica se o resultado não é nulo

        // Decodifica o token para verificar seu conteúdo
        Claims claims = Jwts.parser()
                .setSigningKey("$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .parseClaimsJws(result.getKey())
                .getBody();

        // Verifica as informações do token
        Assertions.assertEquals(username, claims.getSubject()); // Verifica o subject
        Assertions.assertEquals("Token do app", claims.getIssuer()); // Verifica o issuer
        Assertions.assertTrue(claims.getIssuedAt().before(new Date())); // Verifica se a data de emissão é anterior à data atual
        Assertions.assertTrue(claims.getExpiration().after(new Date())); // Verifica se a data de expiração é posterior à data atual

        // Verifica as informações do TokenDto
        Assertions.assertEquals(result.getKey(), result.getKey()); // Verifica o token
        Assertions.assertEquals(username, result.getExtendedInformation()); // Verifica o username
    }

    @Test
    void givenValidToken_whenVerifyToken_thenReturnValidTokenDto() {
        // Arrange
        String token = "validToken";
        Date issuedAt = new Date();
        String username = "username";

        Claims claims = mock(Claims.class);
        when(claims.getIssuedAt()).thenReturn(issuedAt);
        when(claims.getSubject()).thenReturn(username);

        // Mock do Jwts.parser()
        try (MockedStatic<Jwts> mockedJwts = Mockito.mockStatic(Jwts.class)) {
            // Mock do JwtParser
            JwtParser jwtParser = mock(JwtParser.class);
            Jws<Claims> jws = mock(Jws.class);

            when(jws.getBody()).thenReturn(claims);
            when(jwtParser.setSigningKey("$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")).thenReturn(jwtParser);
            when(jwtParser.parseClaimsJws(token)).thenReturn(jws);

            // Configura o mock estático para retornar o JwtParser mockado
            mockedJwts.when(Jwts::parser).thenReturn(jwtParser);

            TokenServiceImpl tokenService = new TokenServiceImpl();

            // Act
            TokenDto result = (TokenDto) tokenService.verifyToken(token);

            // Assert
            Assertions.assertNotNull(result); // Verifica se o resultado não é nulo
            Assertions.assertEquals(token, result.getKey()); // Verifica o token
            Assertions.assertEquals(username, result.getExtendedInformation()); // Verifica o username
        }
    }

    @Test
    void givenInvalidToken_whenVerifyToken_thenThrowException() {
        // Arrange
        String token = "invalidToken";

        // Mock do Jwts.parser()
        try (MockedStatic<Jwts> mockedJwts = Mockito.mockStatic(Jwts.class)) {
            // Mock do JwtParser
            JwtParser jwtParser = mock(JwtParser.class);

            // Configura o mock estático para retornar o JwtParser mockado
            mockedJwts.when(Jwts::parser).thenReturn(jwtParser);

            // Configura o JwtParser mockado para lançar uma exceção
            when(jwtParser.setSigningKey("$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")).thenReturn(jwtParser);
            when(jwtParser.parseClaimsJws(token)).thenThrow(new RuntimeException("Token inválido"));

            TokenServiceImpl tokenService = new TokenServiceImpl();

            // Act & Assert
            Assertions.assertThrows(RuntimeException.class, () -> tokenService.verifyToken(token));
        }
    }
}