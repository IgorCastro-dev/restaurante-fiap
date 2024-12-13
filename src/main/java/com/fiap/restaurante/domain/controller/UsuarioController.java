package com.fiap.restaurante.domain.controller;
import com.fiap.restaurante.domain.dto.UsuarioDto;
import com.fiap.restaurante.domain.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping(value = "/listar")
    public ResponseEntity<List<UsuarioDto>> listarUsuario(){
        return ResponseEntity.ok(usuarioService.listaUsuario());
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<UsuarioDto> cadastrarUsuario(@Valid  @RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuarioDto));
    }

    @GetMapping(value = "/{idUsuario}")
    public ResponseEntity<UsuarioDto> buscarUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(usuarioService.getUsuario(idUsuario));
    }

    @DeleteMapping(value = "/{idUsuario}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Integer idUsuario) {
        usuarioService.deletaUsuario(idUsuario);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }


}
