package com.fiap.restaurante.controller;

import com.fiap.restaurante.domain.controller.ItemCardapioController;
import com.fiap.restaurante.domain.dto.ItemCardapioDto;
import com.fiap.restaurante.domain.services.ItemCardapioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ItemCardapioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemCardapioService itemCardapioService;

    @InjectMocks
    private ItemCardapioController itemCardapioController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemCardapioController).build();
    }

    @Test
    void testCadastrarCardapio() throws Exception {
        ItemCardapioDto itemDto = new ItemCardapioDto();
        when(itemCardapioService.salvarItemCardapio(any(ItemCardapioDto.class))).thenReturn(itemDto);

        mockMvc.perform(post("/cardapio/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                        {
                                            "nome": "Pizza Margherita",
                                            "descricao": "Pizza tradicional com molho de tomate, mussarela e manjericão.",
                                            "preco": 39.90,
                                            "disponivelApenasNoLocal": true,
                                            "caminhoFoto": "/imagens/pizza_margherita.jpg"
                                        }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void testAtualizarCardapio() throws Exception {
        ItemCardapioDto itemDto = new ItemCardapioDto();
        itemDto.setNome("Pizza");  // Adicione os campos obrigatórios
        itemDto.setPreco(BigDecimal.TEN);

        when(itemCardapioService.atualizaCardapio(any(ItemCardapioDto.class), anyLong()))
                .thenReturn(itemDto);

        mockMvc.perform(put("/cardapio/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                        {
                                            "nome": "Pizza Margherita",
                                            "descricao": "Pizza tradicional com molho de tomate, mussarela e manjericão.",
                                            "preco": 39.90,
                                            "disponivelApenasNoLocal": false,
                                            "caminhoFoto": "/imagens/pizza_margherita.jpg"
                                        }
                                """))
                .andExpect(status().isOk());
    }


    @Test
    void testListarCardapio() throws Exception {
        when(itemCardapioService.listarCardapio()).thenReturn(List.of(new ItemCardapioDto()));

        mockMvc.perform(get("/cardapio/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testDeletarCardapio() throws Exception {
        doNothing().when(itemCardapioService).deletaCardapio(anyLong());

        mockMvc.perform(delete("/cardapio/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarCardapio() throws Exception {
        when(itemCardapioService.getCardapio(anyLong())).thenReturn(new ItemCardapioDto());

        mockMvc.perform(get("/cardapio/1"))
                .andExpect(status().isOk());
    }
}
