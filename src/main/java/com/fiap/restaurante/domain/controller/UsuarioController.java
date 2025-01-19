package com.fiap.restaurante.domain.controller;
import com.fiap.restaurante.domain.dto.UsuarioDto;
import com.fiap.restaurante.domain.dto.UsuarioSemSenhaDto;
import com.fiap.restaurante.domain.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Usuario Controller")
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @Operation(summary = "Lista todos os usuários",method = "GET")
    @ApiResponse(responseCode = "200",description = "Lista de usuarios retornada com sucesso")
    @GetMapping(value = "/listar")
    public ResponseEntity<List<UsuarioDto>> listarUsuario(){
        return ResponseEntity.ok(usuarioService.listaUsuario());
    }


    @Operation(summary = "Cadastrar um novo usuário",description = "Cadastra um novo usuário no sistema com as informações fornecidas",method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Usuário com login ou email já existente")
    })
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<UsuarioSemSenhaDto> cadastrarUsuario(@Valid  @RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuarioDto));
    }

    @Operation(summary = "Buscar um usuário pelo ID",description = "Retorna os detalhes de um usuário específico com base no ID fornecido",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado")
    })
    @GetMapping(value = "/{idUsuario}")
    public ResponseEntity<UsuarioDto> buscarUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(usuarioService.getUsuario(idUsuario));
    }

    @Operation(summary = "Deletar um usuário pelo ID",description = "Exclui um usuário do sistema com base no ID fornecido",method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado")
    })
    @DeleteMapping(value = "/{idUsuario}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Integer idUsuario) {
        usuarioService.deletaUsuario(idUsuario);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

    @Operation(summary = "Atualizar um usuário pelo ID",description = "Atualiza as informações de um usuário existente no sistema",method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado")
    })
    @PutMapping(value = "/{idUsuario}")
    public ResponseEntity<UsuarioSemSenhaDto> atualizarUsuario(@Valid @RequestBody UsuarioSemSenhaDto usuarioDto,@PathVariable Integer idUsuario){
        return ResponseEntity.ok(usuarioService.atualizaUsuario(usuarioDto,idUsuario));
    }

}
