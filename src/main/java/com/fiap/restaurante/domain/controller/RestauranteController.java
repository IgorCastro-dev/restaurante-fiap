package com.fiap.restaurante.domain.controller;

import com.fiap.restaurante.domain.dto.RestauranteRequestDto;
import com.fiap.restaurante.domain.dto.RestauranteResponseDto;
import com.fiap.restaurante.domain.dto.UsuarioDto;
import com.fiap.restaurante.domain.services.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não existe"),
    })
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<RestauranteResponseDto> cadastrarRestaurante(@Valid @RequestBody RestauranteRequestDto restauranteRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.salvarRestaurante(restauranteRequestDto));
    }

    @Operation(summary = "Listar todos os restaurantes",method = "GET")
    @ApiResponse(responseCode = "200",description = "Lista de restaurantes retornada com sucesso")
    @GetMapping(value = "/listar")
    public ResponseEntity<List<RestauranteResponseDto>> listarRestaurante(){
        return ResponseEntity.ok(restauranteService.listarRestaurante());
    }


    @Operation(summary = "Deletar um restaurante pelo ID",description = "Exclui um restaurante do sistema com base no ID fornecido",method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "403", description = "Restaurante não autenticado")
    })
    @DeleteMapping(value = "/{idRestaurante}")
    public ResponseEntity<String> deletarRestaurante(@PathVariable Integer idRestaurante) {
        restauranteService.deletaRestaurante(idRestaurante);
        return ResponseEntity.ok("Restaurante deletado com sucesso");
    }


    @Operation(summary = "Atualizar um restaurante pelo ID",description = "Atualiza as informações de um restaurante existente no sistema",method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Restaurante não autenticado")
    })
    @PutMapping(value = "/{idRestaurante}")
    public ResponseEntity<RestauranteResponseDto> atualizarRestaurante(@Valid @RequestBody RestauranteRequestDto restauranteRequestDto, @PathVariable Integer idRestaurante){
        return ResponseEntity.ok(restauranteService.atualizaRestaurante(restauranteRequestDto,idRestaurante));
    }

    @Operation(summary = "Buscar um restaurante pelo ID",description = "Retorna os detalhes de um restaurante específico com base no ID fornecido",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "403", description = "Restaurante não autenticado")
    })
    @GetMapping(value = "/{idRestaurante}")
    public ResponseEntity<RestauranteResponseDto> buscarRestaurante(@PathVariable Integer idRestaurante) {
        return ResponseEntity.ok(restauranteService.getRestaurante(idRestaurante));
    }
}
