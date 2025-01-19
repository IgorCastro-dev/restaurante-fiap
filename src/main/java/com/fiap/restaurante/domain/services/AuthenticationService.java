package com.fiap.restaurante.domain.services;

import com.fiap.restaurante.domain.dto.LoginDto;
import com.fiap.restaurante.domain.dto.TrocaSenhaDto;
import com.fiap.restaurante.domain.entity.Usuario;
import com.fiap.restaurante.domain.repository.UsuarioRepository;
import com.fiap.restaurante.exception.CredencialErradoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public Token autenticate(LoginDto loginDTO) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword());
            var auth = authenticationManager.authenticate(authenticationToken);
            Usuario usuario = (Usuario) auth.getPrincipal();
            return tokenService.allocateToken(usuario.getUsername());
        }catch (BadCredentialsException e){
            throw new CredencialErradoException("Credenciais inválidas.");
        }

    }


    public String trocaSenha(TrocaSenhaDto trocaSenhaDto, String bearerToken) {
        if(!trocaSenhaDto.getNovaSenha().equals(trocaSenhaDto.getConfirmaSenha())){
            throw new CredencialErradoException("Senha de confirmação é diferente");
        }
        String login = getSubjectFromToken(bearerToken);

        Usuario usuario = usuarioRepository.findByLogin(login).get();
        if(!passwordEncoder.matches(trocaSenhaDto.getSenhaAtual(),usuario.getSenha())){
            throw new CredencialErradoException("Senha atual está errada");
        }
        usuario.setSenha(passwordEncoder.encode(trocaSenhaDto.getNovaSenha()));
        usuarioRepository.save(usuario);
        return "Senha alterada com sucesso";
    }

    public String getSubjectFromToken(String bearerToken) {
        String token = bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : bearerToken;

        Claims claims = Jwts.parser()
                .setSigningKey("$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
