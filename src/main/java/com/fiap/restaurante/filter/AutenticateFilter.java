package com.fiap.restaurante.filter;

import com.fiap.restaurante.domain.entity.Usuario;
import com.fiap.restaurante.domain.repository.UsuarioRepository;
import com.fiap.restaurante.exception.UsuarioNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class AutenticateFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        if (Objects.nonNull(token)){
            autenticate(token);
        }
        filterChain.doFilter(request,response);
    }

    private void autenticate(String key) {
        Token token = tokenService.verifyToken(key);
        Usuario usuario = usuarioRepository.findByLogin(token.getExtendedInformation())
                .orElseThrow(()-> new UsuarioNotFoundException("Usuário não encontrado"));
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (Objects.isNull(token) || !token.startsWith("Bearer")){
            return null;
        }
        return token.substring(7);
    }

}
