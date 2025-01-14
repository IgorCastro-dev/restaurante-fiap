package com.fiap.restaurante.domain.controller;

import com.fiap.restaurante.domain.dto.LoginDto;
import com.fiap.restaurante.domain.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid LoginDto loginDTO){
        return ResponseEntity.ok(authenticationService.autenticate(loginDTO));
    }
}
