package com.fiap.restaurante.entity;

import com.fiap.restaurante.domain.entity.ItemCardapio;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class ItemCardapioTest {

    @Test
    void testGettersAndSetters() {
        ItemCardapio item = new ItemCardapio();

        item.setIdItemCardapio(1L);
        item.setNome("Pizza Margherita");
        item.setDescricao("Pizza com molho de tomate, mussarela e manjericão");
        item.setPreco(new BigDecimal("45.90"));
        item.setDisponivelApenasNoLocal(true);
        item.setCaminhoFoto("caminho/foto.jpg");

        assertEquals(1L, item.getIdItemCardapio());
        assertEquals("Pizza Margherita", item.getNome());
        assertEquals("Pizza com molho de tomate, mussarela e manjericão", item.getDescricao());
        assertEquals(new BigDecimal("45.90"), item.getPreco());
        assertTrue(item.isDisponivelApenasNoLocal());
        assertEquals("caminho/foto.jpg", item.getCaminhoFoto());
    }

    @Test
    void testConstructor() {
        Long id = 1L;
        String nome = "Pizza Margherita";
        String descricao = "Pizza com molho de tomate, mussarela e manjericão";
        BigDecimal preco = new BigDecimal("45.90");
        boolean disponivelApenasNoLocal = true;
        String caminhoFoto = "caminho/foto.jpg";

        ItemCardapio item = new ItemCardapio();
        item.setIdItemCardapio(id);
        item.setNome(nome);
        item.setDescricao(descricao);
        item.setPreco(preco);
        item.setDisponivelApenasNoLocal(disponivelApenasNoLocal);
        item.setCaminhoFoto(caminhoFoto);

        assertEquals(id, item.getIdItemCardapio());
        assertEquals(nome, item.getNome());
        assertEquals(descricao, item.getDescricao());
        assertEquals(preco, item.getPreco());
        assertEquals(disponivelApenasNoLocal, item.isDisponivelApenasNoLocal());
        assertEquals(caminhoFoto, item.getCaminhoFoto());
    }

    @Test
    void testEqualsAndHashCode() {
        ItemCardapio item1 = new ItemCardapio();
        item1.setIdItemCardapio(1L);
        item1.setNome("Pizza Margherita");

        ItemCardapio item2 = new ItemCardapio();
        item2.setIdItemCardapio(1L);
        item2.setNome("Pizza Margherita");

        ItemCardapio item3 = new ItemCardapio();
        item3.setIdItemCardapio(2L);
        item3.setNome("Pizza Calabresa");

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
        assertNotEquals(item1, item3);
    }

    @Test
    void testToString() {
        ItemCardapio item = new ItemCardapio();
        item.setIdItemCardapio(1L);
        item.setNome("Pizza Margherita");
        item.setDescricao("Pizza com molho de tomate, mussarela e manjericão");
        item.setPreco(new BigDecimal("45.90"));
        item.setDisponivelApenasNoLocal(true);
        item.setCaminhoFoto("caminho/foto.jpg");

        String toStringResult = item.toString();

        assertTrue(toStringResult.contains("Pizza Margherita"));
        assertTrue(toStringResult.contains("45.90"));
    }
}
