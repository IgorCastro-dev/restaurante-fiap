package com.fiap.restaurante.exception;

import com.fiap.restaurante.application.exception.CredencialErradoException;
import com.fiap.restaurante.presentation.handler.RestExceptionHandler;
import com.fiap.restaurante.application.exception.RestauranteNotFoundException;
import com.fiap.restaurante.application.exception.TipoUsuarioException;
import com.fiap.restaurante.application.exception.TipoUsuarioNotFoundException;
import com.fiap.restaurante.application.exception.UsuarioAlreadyExistsException;
import com.fiap.restaurante.application.exception.UsuarioNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.bind.annotation.*;

@ExtendWith(MockitoExtension.class)
public class RestExceptionHandlerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @BeforeEach
    void setUp() {
        // Configura o MockMvc para usar o RestExceptionHandler
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(restExceptionHandler)
                .build();
    }

    // Controlador de teste para simular exceções
    @RestController
    @RequestMapping("/test")
    static class TestController {
        @GetMapping("/usuario-not-found")
        public void throwUsuarioNotFoundException() {
            throw new UsuarioNotFoundException("Usuário não encontrado");
        }

        @GetMapping("/restaurante-not-found")
        public void throwRestauranteNotFoundException() {
            throw new RestauranteNotFoundException("Restaurante não encontrado");
        }

        @GetMapping("/tipo-usuario-exception")
        public void throwTipoUsuarioException() {
            throw new TipoUsuarioException("Tipo de usuário inválido");
        }

        @GetMapping("/tipo-usuario-not-found")
        public void throwTipoUsuarioNotFoundException() {
            throw new TipoUsuarioNotFoundException("Tipo de usuário não encontrado");
        }

        @GetMapping("/credencial-errado")
        public void throwCredencialErradoException() {
            throw new CredencialErradoException("Credenciais inválidas");
        }

        @GetMapping("/usuario-already-exists")
        public void throwUsuarioAlreadyExistsException() {
            throw new UsuarioAlreadyExistsException("Usuário já existe");
        }

        @PostMapping("/validacao-falha")
        public void validacaoFalha(@Valid @RequestBody TestModel testModel) {
            // Este método não precisa fazer nada, pois a validação será feita automaticamente pelo Spring
        }
    }

    // Classe de modelo para validação
    static class TestModel {
        @NotNull(message = "O campo 'nome' é obrigatório")
        private String nome;

        @Size(min = 5, max = 10, message = "O campo 'descricao' deve ter entre 5 e 10 caracteres")
        private String descricao;

        // Getters e Setters
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
    }

    @Test
    void givenUsuarioNotFoundException_whenHandleException_thenReturnNotFound() throws Exception {
        mockMvc.perform(get("/test/usuario-not-found"))
                .andExpect(status().isNotFound()) // Verifica o status HTTP 404
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND")) // Verifica o status no JSON
                .andExpect(jsonPath("$.message").value("Usuário não encontrado")); // Verifica a mensagem no JSON
    }

    @Test
    void givenRestauranteNotFoundException_whenHandleException_thenReturnNotFound() throws Exception {
        mockMvc.perform(get("/test/restaurante-not-found"))
                .andExpect(status().isNotFound()) // Verifica o status HTTP 404
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND")) // Verifica o status no JSON
                .andExpect(jsonPath("$.message").value("Restaurante não encontrado")); // Verifica a mensagem no JSON
    }

    @Test
    void givenTipoUsuarioException_whenHandleException_thenReturnBadRequest() throws Exception {
        mockMvc.perform(get("/test/tipo-usuario-exception"))
                .andExpect(status().isBadRequest()) // Verifica o status HTTP 400
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST")) // Verifica o status no JSON
                .andExpect(jsonPath("$.message").value("Tipo de usuário inválido")); // Verifica a mensagem no JSON
    }

    @Test
    void givenTipoUsuarioNotFoundException_whenHandleException_thenReturnNotFound() throws Exception {
        mockMvc.perform(get("/test/tipo-usuario-not-found"))
                .andExpect(status().isNotFound()) // Verifica o status HTTP 404
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND")) // Verifica o status no JSON
                .andExpect(jsonPath("$.message").value("Tipo de usuário não encontrado")); // Verifica a mensagem no JSON
    }
}