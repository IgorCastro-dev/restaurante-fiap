package com.fiap.restaurante.services;

import com.fiap.restaurante.presentation.dto.TokenDto;
import com.fiap.restaurante.application.services.TokenServiceImpl;
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
        String username = "username";
        TokenServiceImpl tokenService = new TokenServiceImpl();

        TokenDto result = (TokenDto) tokenService.allocateToken(username);

        Assertions.assertNotNull(result);

        Claims claims = Jwts.parser()
                .setSigningKey("$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .parseClaimsJws(result.getKey())
                .getBody();

        Assertions.assertEquals(username, claims.getSubject());
        Assertions.assertEquals("Token do app", claims.getIssuer());
        Assertions.assertTrue(claims.getIssuedAt().before(new Date()));
        Assertions.assertTrue(claims.getExpiration().after(new Date()));

        Assertions.assertEquals(result.getKey(), result.getKey());
        Assertions.assertEquals(username, result.getExtendedInformation());
    }

    @Test
    void givenValidToken_whenVerifyToken_thenReturnValidTokenDto() {
        String token = "validToken";
        Date issuedAt = new Date();
        String username = "username";

        Claims claims = mock(Claims.class);
        when(claims.getIssuedAt()).thenReturn(issuedAt);
        when(claims.getSubject()).thenReturn(username);

        try (MockedStatic<Jwts> mockedJwts = Mockito.mockStatic(Jwts.class)) {
            JwtParser jwtParser = mock(JwtParser.class);
            Jws<Claims> jws = mock(Jws.class);

            when(jws.getBody()).thenReturn(claims);
            when(jwtParser.setSigningKey("$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")).thenReturn(jwtParser);
            when(jwtParser.parseClaimsJws(token)).thenReturn(jws);

            mockedJwts.when(Jwts::parser).thenReturn(jwtParser);

            TokenServiceImpl tokenService = new TokenServiceImpl();

            TokenDto result = (TokenDto) tokenService.verifyToken(token);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(token, result.getKey());
            Assertions.assertEquals(username, result.getExtendedInformation());
        }
    }

    @Test
    void givenInvalidToken_whenVerifyToken_thenThrowException() {
        String token = "invalidToken";

        try (MockedStatic<Jwts> mockedJwts = Mockito.mockStatic(Jwts.class)) {
            JwtParser jwtParser = mock(JwtParser.class);

            mockedJwts.when(Jwts::parser).thenReturn(jwtParser);

            when(jwtParser.setSigningKey("$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")).thenReturn(jwtParser);
            when(jwtParser.parseClaimsJws(token)).thenThrow(new RuntimeException("Token inválido"));

            TokenServiceImpl tokenService = new TokenServiceImpl();

            Assertions.assertThrows(RuntimeException.class, () -> tokenService.verifyToken(token));
        }
    }
}