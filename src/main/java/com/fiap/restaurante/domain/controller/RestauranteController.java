package com.fiap.restaurante.domain.controller;

import com.fiap.restaurante.domain.dto.RestauranteRequestDto;
import com.fiap.restaurante.domain.dto.RestauranteResponseDto;
import com.fiap.restaurante.domain.services.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Restaurante Controller")
@RestController
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Operation(summary = "Cadastrar um novo restaurante",description = "Cadastra um novo restaurante no sistema com as informações fornecidas",method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não existe"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<RestauranteResponseDto> cadastrarRestaurante(@Valid @RequestBody RestauranteRequestDto restauranteRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.salvarRestaurante(restauranteRequestDto));
    }
}
