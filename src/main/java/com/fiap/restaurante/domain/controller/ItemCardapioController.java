package com.fiap.restaurante.domain.controller;


import com.fiap.restaurante.domain.dto.ItemCardapioDto;
import com.fiap.restaurante.domain.services.ItemCardapioService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
