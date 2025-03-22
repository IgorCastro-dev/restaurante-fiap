package com.fiap.restaurante.controller;

import com.fiap.restaurante.domain.controller.RestauranteController;
import com.fiap.restaurante.domain.dto.RestauranteRequestDto;
import com.fiap.restaurante.domain.dto.RestauranteResponseDto;
import com.fiap.restaurante.domain.services.RestauranteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RestauranteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestauranteService restauranteService;

    @InjectMocks
    private RestauranteController restauranteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restauranteController).build();
    }

    @Test
    void testCadastrarRestaurante() throws Exception {
        // Cria um DTO de requisição simulado
        RestauranteRequestDto requestDto = new RestauranteRequestDto();
        requestDto.setNome("Restaurante Teste");

        // Cria um DTO de resposta simulado
        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNome("Restaurante Teste");

        // Configura o comportamento simulado do serviço
        when(restauranteService.salvarRestaurante(any(RestauranteRequestDto.class)))
                .thenReturn(responseDto);

        // Executa a requisição simulada
        mockMvc.perform(post("/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                                  "nome": "Restaurante Exemplo",
                                                  "endereco": "Rua Exemplo, 123",
                                                  "tipoCozinha": "Italiana",
                                                  "horarioFuncionamento": "18:00 - 23:00",
                                                  "idUsuario": 1
                                                }
                                """))
                .andExpect(status().isCreated()) // Verifica o status HTTP 201
                .andExpect(jsonPath("$.nome").value("Restaurante Teste")); // Verifica apenas o nome no JSON de resposta
    }
    @Test
    void testListarRestaurante() throws Exception {
        // Cria um DTO de resposta simulado
        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNome("Restaurante Teste");

        // Configura o comportamento simulado do serviço
        when(restauranteService.listarRestaurante())
                .thenReturn(List.of(responseDto));

        // Executa a requisição simulada
        mockMvc.perform(get("/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1)) // Verifica o tamanho da lista
                .andExpect(jsonPath("$[0].nome").value("Restaurante Teste")); // Verifica o nome do primeiro elemento
    }

    @Test
    void testBuscarRestaurante() throws Exception {
        // Cria um DTO de resposta simulado
        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNome("Restaurante Teste");

        // Configura o comportamento simulado do serviço
        when(restauranteService.getRestaurante(anyInt())).thenReturn(responseDto);

        // Executa a requisição simulada
        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Restaurante Teste")); // Verifica apenas o nome no JSON de resposta
    }

    @Test
    void testAtualizarRestaurante() throws Exception {
        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNome("Restaurante Atualizado");
        responseDto.setEndereco("Rua Exemplo, 123");
        responseDto.setTipoCozinha("Italiana");
        responseDto.setHorarioFuncionamento("18:00 - 24:00");

        // Simula o serviço retornando o DTO com o ID esperado
        when(restauranteService.atualizaRestaurante(any(RestauranteRequestDto.class), eq(1))) // Parêntese fechado corretamente
                .thenReturn(responseDto);

        mockMvc.perform(put("/1")  // ID passado na URL
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Restaurante Atualizado",
                                  "endereco": "Rua Exemplo, 123",
                                  "tipoCozinha": "Italiana",
                                  "horarioFuncionamento": "18:00 - 24:00",
                                  "idUsuario": 1
                                }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Restaurante Atualizado"))
                .andExpect(jsonPath("$.endereco").value("Rua Exemplo, 123"))
                .andExpect(jsonPath("$.tipoCozinha").value("Italiana"))
                .andExpect(jsonPath("$.horarioFuncionamento").value("18:00 - 24:00"));
    }

    @Test
    void testDeletarRestaurante() throws Exception {
        mockMvc.perform(delete("/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Restaurante deletado com sucesso"));
    }
}
