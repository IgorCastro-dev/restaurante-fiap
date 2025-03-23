package com.fiap.restaurante.services;

import com.fiap.restaurante.presentation.dto.ItemCardapioDto;
import com.fiap.restaurante.domain.entity.ItemCardapio;
import com.fiap.restaurante.domain.repository.ItemCardapioRepository;
import com.fiap.restaurante.application.services.ItemCardapioService;
import com.fiap.restaurante.application.exception.UsuarioNotFoundException;
import com.fiap.restaurante.infraestructure.mapper.ItemCardapioMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemCardapioServiceTest {

    @Mock
    private ItemCardapioMapper itemCardapioMapper;

    @Mock
    private ItemCardapioRepository itemCardapioRepository;

    @InjectMocks
    private ItemCardapioService itemCardapioService;

    @Test
    @Transactional
    void givenValidItemCardapioDto_whenSalvarItemCardapio_thenReturnItemCardapioDto() {

        // Arrange
        ItemCardapioDto itemCardapioDto = new ItemCardapioDto();
        itemCardapioDto.setNome("Item Teste");
        itemCardapioDto.setCaminhoFoto("caminho/foto.jpg");
        itemCardapioDto.setDescricao("Descrição Teste");
        itemCardapioDto.setPreco(BigDecimal.TEN);
        itemCardapioDto.setDisponivelApenasNoLocal(true);

        ItemCardapio itemCardapio = new ItemCardapio();
        itemCardapio.setIdItemCardapio(1L);
        itemCardapio.setNome("Item Teste");
        itemCardapio.setCaminhoFoto("caminho/foto.jpg");
        itemCardapio.setDescricao("Descrição Teste");
        itemCardapio.setPreco(BigDecimal.TEN);
        itemCardapio.setDisponivelApenasNoLocal(true);

        when(itemCardapioMapper.dtoToEntity(itemCardapioDto)).thenReturn(itemCardapio);
        when(itemCardapioRepository.save(itemCardapio)).thenReturn(itemCardapio);
        when(itemCardapioMapper.entityToDto(itemCardapio)).thenReturn(itemCardapioDto);

        // Act
        ItemCardapioDto result = itemCardapioService.salvarItemCardapio(itemCardapioDto);

        // Assert
        Assertions.assertEquals(itemCardapioDto, result);
        Mockito.verify(itemCardapioMapper, Mockito.times(1)).dtoToEntity(itemCardapioDto);
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).save(itemCardapio);
        Mockito.verify(itemCardapioMapper, Mockito.times(1)).entityToDto(itemCardapio);
    }

    @Test
    @Transactional
    void givenValidItemCardapioDto_whenAtualizaCardapio_thenReturnItemCardapioDto() {
        // Arrange
        Long idCardapio = 1L;
        ItemCardapioDto itemCardapioDto = new ItemCardapioDto();
        itemCardapioDto.setNome("Item Atualizado");
        itemCardapioDto.setCaminhoFoto("caminho/atualizado.jpg");
        itemCardapioDto.setDescricao("Descrição Atualizada");
        itemCardapioDto.setPreco(BigDecimal.valueOf(15));
        itemCardapioDto.setDisponivelApenasNoLocal(false);

        ItemCardapio itemCardapioExistente = new ItemCardapio();
        itemCardapioExistente.setIdItemCardapio(idCardapio);
        itemCardapioExistente.setNome("Item Teste");
        itemCardapioExistente.setCaminhoFoto("caminho/foto.jpg");
        itemCardapioExistente.setDescricao("Descrição Teste");
        itemCardapioExistente.setPreco(BigDecimal.TEN);
        itemCardapioExistente.setDisponivelApenasNoLocal(true);

        when(itemCardapioRepository.findById(idCardapio)).thenReturn(Optional.of(itemCardapioExistente));
        when(itemCardapioRepository.save(itemCardapioExistente)).thenReturn(itemCardapioExistente);
        when(itemCardapioMapper.entityToDto(itemCardapioExistente)).thenReturn(itemCardapioDto);

        // Act
        ItemCardapioDto result = itemCardapioService.atualizaCardapio(itemCardapioDto, idCardapio);

        // Assert
        Assertions.assertEquals(itemCardapioDto, result);
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).findById(idCardapio);
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).save(itemCardapioExistente);
        Mockito.verify(itemCardapioMapper, Mockito.times(1)).entityToDto(itemCardapioExistente);
    }

    @Test
    void givenInvalidId_whenAtualizaCardapio_thenThrowUsuarioNotFoundException() {
        // Arrange
        Long idCardapio = 1L;
        ItemCardapioDto itemCardapioDto = new ItemCardapioDto();

        when(itemCardapioRepository.findById(idCardapio)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(UsuarioNotFoundException.class, () ->
                itemCardapioService.atualizaCardapio(itemCardapioDto, idCardapio));
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).findById(idCardapio);
    }

    @Test
    void givenItems_whenListarCardapio_thenReturnListOfItemCardapioDto() {
        // Arrange
        ItemCardapio itemCardapio = new ItemCardapio();
        itemCardapio.setIdItemCardapio(1L);
        itemCardapio.setNome("Item Teste");

        ItemCardapioDto itemCardapioDto = new ItemCardapioDto();
        itemCardapioDto.setNome("Item Teste");

        when(itemCardapioRepository.findAll()).thenReturn(List.of(itemCardapio));
        when(itemCardapioMapper.entitiesToDto(List.of(itemCardapio))).thenReturn(List.of(itemCardapioDto));

        // Act
        List<ItemCardapioDto> result = itemCardapioService.listarCardapio();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(itemCardapioDto, result.get(0));
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).findAll();
        Mockito.verify(itemCardapioMapper, Mockito.times(1)).entitiesToDto(List.of(itemCardapio));
    }

    @Test
    void givenValidId_whenDeletaCardapio_thenDeleteItemCardapio() {
        // Arrange
        Long idCardapio = 1L;
        ItemCardapio itemCardapio = new ItemCardapio();
        itemCardapio.setIdItemCardapio(idCardapio);

        when(itemCardapioRepository.findById(idCardapio)).thenReturn(Optional.of(itemCardapio));

        // Act
        itemCardapioService.deletaCardapio(idCardapio);

        // Assert
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).findById(idCardapio);
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).delete(itemCardapio);
    }

    @Test
    void givenInvalidId_whenDeletaCardapio_thenThrowUsuarioNotFoundException() {
        // Arrange
        Long idCardapio = 1L;

        when(itemCardapioRepository.findById(idCardapio)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(UsuarioNotFoundException.class, () ->
                itemCardapioService.deletaCardapio(idCardapio));
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).findById(idCardapio);
    }

    @Test
    void givenValidId_whenGetCardapio_thenReturnItemCardapioDto() {
        // Arrange
        Long idCardapio = 1L;
        ItemCardapio itemCardapio = new ItemCardapio();
        itemCardapio.setIdItemCardapio(idCardapio);

        ItemCardapioDto itemCardapioDto = new ItemCardapioDto();

        when(itemCardapioRepository.findById(idCardapio)).thenReturn(Optional.of(itemCardapio));
        when(itemCardapioMapper.entityToDto(itemCardapio)).thenReturn(itemCardapioDto);

        // Act
        ItemCardapioDto result = itemCardapioService.getCardapio(idCardapio);

        // Assert
        Assertions.assertEquals(itemCardapioDto, result);
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).findById(idCardapio);
        Mockito.verify(itemCardapioMapper, Mockito.times(1)).entityToDto(itemCardapio);
    }

    @Test
    void givenInvalidId_whenGetCardapio_thenThrowUsuarioNotFoundException() {
        // Arrange
        Long idCardapio = 1L;

        when(itemCardapioRepository.findById(idCardapio)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(UsuarioNotFoundException.class, () ->
                itemCardapioService.getCardapio(idCardapio));
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).findById(idCardapio);
    }

    @Test
    void givenValidId_whenGetCardapioByid_thenReturnItemCardapio() {
        // Arrange
        Long idCardapio = 1L;
        ItemCardapio itemCardapio = new ItemCardapio();
        itemCardapio.setIdItemCardapio(idCardapio);

        when(itemCardapioRepository.findById(idCardapio)).thenReturn(Optional.of(itemCardapio));

        // Act
        ItemCardapio result = itemCardapioService.getCardapioByid(idCardapio);

        // Assert
        Assertions.assertEquals(itemCardapio, result);
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).findById(idCardapio);
    }

    @Test
    void givenInvalidId_whenGetCardapioByid_thenThrowUsuarioNotFoundException() {
        // Arrange
        Long idCardapio = 1L;

        when(itemCardapioRepository.findById(idCardapio)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(UsuarioNotFoundException.class, () ->
                itemCardapioService.getCardapioByid(idCardapio));
        Mockito.verify(itemCardapioRepository, Mockito.times(1)).findById(idCardapio);
    }
}