package com.fiap.restaurante.domain.controller;

import com.fiap.restaurante.domain.dto.LoginDto;
import com.fiap.restaurante.domain.dto.TrocaSenhaDto;
import com.fiap.restaurante.domain.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class LoginController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid LoginDto loginDTO){
        return ResponseEntity.ok(authenticationService.autenticate(loginDTO));
    }

    @PostMapping("/troca-senha")
    public ResponseEntity<String> login(@RequestBody @Valid TrocaSenhaDto trocaSenhaDto,@RequestHeader("Authorization") String authorizationHeader){
        return ResponseEntity.ok(authenticationService.trocaSenha(trocaSenhaDto, authorizationHeader));
    }
}
