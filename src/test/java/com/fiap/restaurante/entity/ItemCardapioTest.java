package com.fiap.restaurante.entity;

import com.fiap.restaurante.domain.entity.ItemCardapio;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class ItemCardapioTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        ItemCardapio item = new ItemCardapio();

        // Act
        item.setIdItemCardapio(1L);
        item.setNome("Pizza Margherita");
        item.setDescricao("Pizza com molho de tomate, mussarela e manjericão");
        item.setPreco(new BigDecimal("45.90"));
        item.setDisponivelApenasNoLocal(true);
        item.setCaminhoFoto("caminho/foto.jpg");

        // Assert
        assertEquals(1L, item.getIdItemCardapio());
        assertEquals("Pizza Margherita", item.getNome());
        assertEquals("Pizza com molho de tomate, mussarela e manjericão", item.getDescricao());
        assertEquals(new BigDecimal("45.90"), item.getPreco());
        assertTrue(item.isDisponivelApenasNoLocal());
        assertEquals("caminho/foto.jpg", item.getCaminhoFoto());
    }

    @Test
    void testConstructor() {
        // Arrange
        Long id = 1L;
        String nome = "Pizza Margherita";
        String descricao = "Pizza com molho de tomate, mussarela e manjericão";
        BigDecimal preco = new BigDecimal("45.90");
        boolean disponivelApenasNoLocal = true;
        String caminhoFoto = "caminho/foto.jpg";

        // Act
        ItemCardapio item = new ItemCardapio();
        item.setIdItemCardapio(id);
        item.setNome(nome);
        item.setDescricao(descricao);
        item.setPreco(preco);
        item.setDisponivelApenasNoLocal(disponivelApenasNoLocal);
        item.setCaminhoFoto(caminhoFoto);

        // Assert
        assertEquals(id, item.getIdItemCardapio());
        assertEquals(nome, item.getNome());
        assertEquals(descricao, item.getDescricao());
        assertEquals(preco, item.getPreco());
        assertEquals(disponivelApenasNoLocal, item.isDisponivelApenasNoLocal());
        assertEquals(caminhoFoto, item.getCaminhoFoto());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ItemCardapio item1 = new ItemCardapio();
        item1.setIdItemCardapio(1L);
        item1.setNome("Pizza Margherita");

        ItemCardapio item2 = new ItemCardapio();
        item2.setIdItemCardapio(1L);
        item2.setNome("Pizza Margherita");

        ItemCardapio item3 = new ItemCardapio();
        item3.setIdItemCardapio(2L);
        item3.setNome("Pizza Calabresa");

        // Assert
        assertEquals(item1, item2); // Verifica se os objetos são iguais
        assertEquals(item1.hashCode(), item2.hashCode()); // Verifica se os hashCodes são iguais
        assertNotEquals(item1, item3); // Verifica se os objetos são diferentes
    }

    @Test
    void testToString() {
        // Arrange
        ItemCardapio item = new ItemCardapio();
        item.setIdItemCardapio(1L);
        item.setNome("Pizza Margherita");
        item.setDescricao("Pizza com molho de tomate, mussarela e manjericão");
        item.setPreco(new BigDecimal("45.90"));
        item.setDisponivelApenasNoLocal(true);
        item.setCaminhoFoto("caminho/foto.jpg");

        // Act
        String toStringResult = item.toString();

        // Assert
        assertTrue(toStringResult.contains("Pizza Margherita")); // Verifica se o nome está na string
        assertTrue(toStringResult.contains("45.90")); // Verifica se o preço está na string
    }
}
