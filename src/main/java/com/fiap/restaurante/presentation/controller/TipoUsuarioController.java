package com.fiap.restaurante.presentation.controller;

import com.fiap.restaurante.presentation.dto.TipoUsuarioDto;
import com.fiap.restaurante.application.services.TipoUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipo-usuario")
public class TipoUsuarioController {

    @Autowired
    TipoUsuarioService tipoUsuarioService;


    @Operation(summary = "Lista todos os tipos de usuários",method = "GET")
    @ApiResponse(responseCode = "200",description = "Lista de tipos de usuarios retornada com sucesso")
    @GetMapping("/listar")
    public ResponseEntity<List<String>> listarTiposUsuarios(){
        return ResponseEntity.ok(tipoUsuarioService.listar());
    }



    @Operation(summary = "Associa tipo de usuário",description = "Associa um tipo de usuário ao usuário passando o id do usuário na url e o nome do tipo de usuário no corpo da requisição",method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário associado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado")
    })
    @PutMapping("/{idUsuario}")
    public ResponseEntity<String> associarTipoUsuario(@PathVariable("idUsuario") Integer idUsuario,@RequestBody TipoUsuarioDto tipoUsuario){
        tipoUsuarioService.associaTipoUsuario(idUsuario,tipoUsuario.getTipoUsuario());
        return ResponseEntity.ok("Tipo atualizado");
    }
}
