package com.fiap.restaurante.controller;

import com.fiap.restaurante.domain.controller.UsuarioController;
import com.fiap.restaurante.domain.dto.UsuarioDto;
import com.fiap.restaurante.domain.dto.UsuarioSemSenhaDto;
import com.fiap.restaurante.domain.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void testListarUsuario() throws Exception {
        // Cria um DTO de resposta simulado
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNome("Usuário Teste");
        usuarioDto.setEmail("teste@example.com");
        usuarioDto.setLogin("teste");
        usuarioDto.setSenha("senha123");
        usuarioDto.setEndereco("Rua Teste, 123");

        // Configura o comportamento simulado do serviço
        when(usuarioService.listaUsuario()).thenReturn(List.of(usuarioDto));

        // Executa a requisição simulada
        mockMvc.perform(get("/usuario/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Usuário Teste"))
                .andExpect(jsonPath("$[0].email").value("teste@example.com"))
                .andExpect(jsonPath("$[0].login").value("teste"))
                .andExpect(jsonPath("$[0].endereco").value("Rua Teste, 123"));
    }


    @Test
    void testBuscarUsuario() throws Exception {
        // Cria um DTO de resposta simulado
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNome("Usuário Teste");
        usuarioDto.setEmail("teste@example.com");
        usuarioDto.setLogin("teste");
        usuarioDto.setSenha("senha123");
        usuarioDto.setEndereco("Rua Teste, 123");

        // Configura o comportamento simulado do serviço
        when(usuarioService.getUsuario(anyInt())).thenReturn(usuarioDto);

        // Executa a requisição simulada
        mockMvc.perform(get("/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Usuário Teste"))
                .andExpect(jsonPath("$.email").value("teste@example.com"))
                .andExpect(jsonPath("$.login").value("teste"))
                .andExpect(jsonPath("$.endereco").value("Rua Teste, 123"));
    }

    @Test
    void testDeletarUsuario() throws Exception {
        // Configura o comportamento simulado do serviço
        doNothing().when(usuarioService).deletaUsuario(anyInt());

        // Executa a requisição simulada
        mockMvc.perform(delete("/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário deletado com sucesso"));
    }

    @Test
    void testAtualizarUsuario() throws Exception {
        // Cria um DTO de resposta simulado
        UsuarioSemSenhaDto usuarioSemSenhaDto = new UsuarioSemSenhaDto();
        usuarioSemSenhaDto.setNome("Usuário Atualizado");
        usuarioSemSenhaDto.setEmail("atualizado@example.com");
        usuarioSemSenhaDto.setLogin("atualizado");
        usuarioSemSenhaDto.setEndereco("Rua Atualizada, 123");

        // Configura o comportamento simulado do serviço
        when(usuarioService.atualizaUsuario(any(UsuarioSemSenhaDto.class), anyInt())).thenReturn(usuarioSemSenhaDto);

        // Executa a requisição simulada
        mockMvc.perform(put("/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Usuário Atualizado",
                                  "email": "atualizado@example.com",
                                  "login": "atualizado",
                                  "endereco": "Rua Atualizada, 123"
                                }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Usuário Atualizado"))
                .andExpect(jsonPath("$.email").value("atualizado@example.com"))
                .andExpect(jsonPath("$.login").value("atualizado"))
                .andExpect(jsonPath("$.endereco").value("Rua Atualizada, 123"));
    }
}