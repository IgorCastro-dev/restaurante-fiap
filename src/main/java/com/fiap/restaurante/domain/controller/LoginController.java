package com.fiap.restaurante.domain.controller;

import com.fiap.restaurante.domain.dto.LoginDto;
import com.fiap.restaurante.domain.dto.TrocaSenhaDto;
import com.fiap.restaurante.domain.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.Token;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Validação Controller")
@RestController
public class LoginController {
    @Autowired
    AuthenticationService authenticationService;


    @Operation(summary = "Autentica o usuário e retorna um token JWT",
            description = "Este endpoint autentica o usuário utilizando login e senha, e retorna um token JWT válido para acesso aos recursos protegidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido, retorna o token JWT."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas.")
    })
    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid LoginDto loginDTO){
        return ResponseEntity.ok(authenticationService.autenticate(loginDTO));
    }


    @Operation(summary = "Troca a senha do usuário",
            description = "Este endpoint permite que o usuário troque a senha atual, fornecendo a senha atual e a nova senha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado")
    })
    @PostMapping("/troca-senha")
    public ResponseEntity<String> login(@RequestBody @Valid TrocaSenhaDto trocaSenhaDto,@RequestHeader("Authorization") String authorizationHeader){
        return ResponseEntity.ok(authenticationService.trocaSenha(trocaSenhaDto, authorizationHeader));
    }
}
