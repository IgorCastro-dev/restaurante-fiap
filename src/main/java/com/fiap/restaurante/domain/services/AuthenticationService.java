package com.fiap.restaurante.domain.services;

import com.fiap.restaurante.domain.dto.LoginDto;
import com.fiap.restaurante.domain.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    public Token autenticate(LoginDto loginDTO) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword());
            var auth = authenticationManager.authenticate(authenticationToken);
            Usuario usuario = (Usuario) auth.getPrincipal();
            return tokenService.allocateToken(usuario.getUsername());
        }catch (BadCredentialsException e){
            throw new RuntimeException(e.getMessage(),e);
        }

    }
}
