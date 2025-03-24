package com.fiap.restaurante.controller;

import com.fiap.restaurante.presentation.dto.RestauranteRequestDto;
import com.fiap.restaurante.presentation.dto.RestauranteResponseDto;
import com.fiap.restaurante.application.services.RestauranteService;
import com.fiap.restaurante.presentation.controller.RestauranteController;
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
        RestauranteRequestDto requestDto = new RestauranteRequestDto();
        requestDto.setNome("Restaurante Teste");

        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNome("Restaurante Teste");

        when(restauranteService.salvarRestaurante(any(RestauranteRequestDto.class)))
                .thenReturn(responseDto);

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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Restaurante Teste"));
    }
    @Test
    void testListarRestaurante() throws Exception {

        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNome("Restaurante Teste");

        when(restauranteService.listarRestaurante())
                .thenReturn(List.of(responseDto));

        mockMvc.perform(get("/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Restaurante Teste"));
    }

    @Test
    void testBuscarRestaurante() throws Exception {
        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNome("Restaurante Teste");

        when(restauranteService.getRestaurante(anyInt())).thenReturn(responseDto);

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Restaurante Teste"));
    }

    @Test
    void testAtualizarRestaurante() throws Exception {
        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNome("Restaurante Atualizado");
        responseDto.setEndereco("Rua Exemplo, 123");
        responseDto.setTipoCozinha("Italiana");
        responseDto.setHorarioFuncionamento("18:00 - 24:00");

        when(restauranteService.atualizaRestaurante(any(RestauranteRequestDto.class), eq(1)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/1")
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
