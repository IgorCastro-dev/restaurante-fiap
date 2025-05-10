package com.fiap.restaurante.entity;

import com.fiap.restaurante.domain.entity.Restaurante;
import com.fiap.restaurante.domain.entity.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RestauranteTest {

    @Test
    void testGettersAndSetters() {
        Restaurante restaurante = new Restaurante();
        Usuario dono = new Usuario();
        dono.setIdUsusario(1);

        restaurante.setIdRestaurante(1L);
        restaurante.setNome("Restaurante Teste");
        restaurante.setEndereco("Endereço Teste");
        restaurante.setTipoCozinha("Cozinha Teste");
        restaurante.setHorarioFuncionamento("Horário Teste");
        restaurante.setDono(dono);

        assertEquals(1L, restaurante.getIdRestaurante());
        assertEquals("Restaurante Teste", restaurante.getNome());
        assertEquals("Endereço Teste", restaurante.getEndereco());
        assertEquals("Cozinha Teste", restaurante.getTipoCozinha());
        assertEquals("Horário Teste", restaurante.getHorarioFuncionamento());
        assertEquals(dono, restaurante.getDono());
    }

    @Test
    void testConstructor() {

        Long id = 1L;
        String nome = "Restaurante Teste";
        String endereco = "Endereço Teste";
        String tipoCozinha = "Cozinha Teste";
        String horarioFuncionamento = "Horário Teste";
        Usuario dono = new Usuario();
        dono.setIdUsusario(1);

        Restaurante restaurante = Restaurante.builder()
                .idRestaurante(id)
                .nome(nome)
                .endereco(endereco)
                .tipoCozinha(tipoCozinha)
                .horarioFuncionamento(horarioFuncionamento)
                .dono(dono)
                .build();

        assertEquals(id, restaurante.getIdRestaurante());
        assertEquals(nome, restaurante.getNome());
        assertEquals(endereco, restaurante.getEndereco());
        assertEquals(tipoCozinha, restaurante.getTipoCozinha());
        assertEquals(horarioFuncionamento, restaurante.getHorarioFuncionamento());
        assertEquals(dono, restaurante.getDono());
    }

    @Test
    void testEqualsAndHashCode() {
        Usuario dono = new Usuario();
        dono.setIdUsusario(1);

        Restaurante restaurante1 = Restaurante.builder()
                .idRestaurante(1L)
                .nome("Restaurante Teste")
                .dono(dono)
                .build();

        Restaurante restaurante2 = Restaurante.builder()
                .idRestaurante(1L)
                .nome("Restaurante Teste")
                .dono(dono)
                .build();

        Restaurante restaurante3 = Restaurante.builder()
                .idRestaurante(2L)
                .nome("Outro Restaurante")
                .dono(dono)
                .build();

        assertEquals(restaurante1, restaurante2);
        assertEquals(restaurante1.hashCode(), restaurante2.hashCode());
        assertNotEquals(restaurante1, restaurante3);
    }

    @Test
    void testToString() {
        Usuario dono = new Usuario();
        dono.setIdUsusario(1);

        Restaurante restaurante = Restaurante.builder()
                .idRestaurante(1L)
                .nome("Restaurante Teste")
                .endereco("Endereço Teste")
                .tipoCozinha("Cozinha Teste")
                .horarioFuncionamento("Horário Teste")
                .dono(dono)
                .build();


        String toStringResult = restaurante.toString();


        assertTrue(toStringResult.contains("Restaurante Teste"));
        assertTrue(toStringResult.contains("Endereço Teste"));
        assertTrue(toStringResult.contains("Cozinha Teste"));
        assertTrue(toStringResult.contains("Horário Teste"));
    }
}