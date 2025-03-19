package com.fiap.restaurante.domain.controller;


import com.fiap.restaurante.domain.dto.ItemCardapioDto;
import com.fiap.restaurante.domain.dto.RestauranteResponseDto;
import com.fiap.restaurante.domain.dto.UsuarioSemSenhaDto;
import com.fiap.restaurante.domain.services.ItemCardapioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Item Cardapio Controller")
@RestController
@RequestMapping(value = "/cardapio")
public class ItemCardapioController {


    @Autowired
    private ItemCardapioService itemCardapioService;

    @Operation(summary = "Cadastrar um novo cardapio",description = "Cadastra um novo cardapio no sistema com as informações fornecidas",method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cardapio cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<ItemCardapioDto> cadastrarCardapio(@Valid @RequestBody ItemCardapioDto itemCardapioDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemCardapioService.salvarItemCardapio(itemCardapioDto));
    }

    @Operation(summary = "Atualizar um item do cardapio pelo ID",description = "Atualiza as informações de um item do cardapio existente no sistema",method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item do ardapio atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item do Cardapio não encontrado"),
            @ApiResponse(responseCode = "400", description = "Item do Dados inválidos fornecidos")
    })
    @PutMapping(value = "/{idCardapio}")
    public ResponseEntity<ItemCardapioDto> atualizarCardapio(@Valid @RequestBody ItemCardapioDto itemCardapioDto, @PathVariable Long idCardapio){
        return ResponseEntity.ok(itemCardapioService.atualizaCardapio(itemCardapioDto,idCardapio));
    }

    @Operation(summary = "Listar todos os itens do cardapio",method = "GET")
    @ApiResponse(responseCode = "200",description = "Lista de itens do retornada com sucesso")
    @GetMapping(value = "/listar")
    public ResponseEntity<List<ItemCardapioDto>> listarCardapio(){
        return ResponseEntity.ok(itemCardapioService.listarCardapio());
    }
}
