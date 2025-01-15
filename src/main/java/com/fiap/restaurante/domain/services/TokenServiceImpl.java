package com.fiap.restaurante.domain.services;

import com.fiap.restaurante.domain.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public Token allocateToken(String username) {
        Date today = new Date();
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .setSubject(username)
                .setIssuer("Token do app")
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime() + 1000 * 60 * 15))
                .compact();
        return new TokenDto(jwt,today.getTime(),username);
    }

    @Override
    public Token verifyToken(String key) {
        Claims claims = Jwts.parser()
                .setSigningKey("$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .parseClaimsJws(key)
                .getBody();
        return new TokenDto(key,claims.getIssuedAt().getTime(),claims.getSubject());
    }
}
