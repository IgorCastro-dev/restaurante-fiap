package com.fiap.restaurante.domain.services;

import com.fiap.restaurante.domain.dto.ItemCardapioDto;
import com.fiap.restaurante.domain.dto.RestauranteResponseDto;
import com.fiap.restaurante.domain.entity.ItemCardapio;
import com.fiap.restaurante.domain.repository.ItemCardapioRepository;
import com.fiap.restaurante.exception.UsuarioNotFoundException;
import com.fiap.restaurante.util.mapper.ItemCardapioMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemCardapioService {

    private static final String CARDAPIO_NOT_FOUND_MESSAGE = "Item do cardápio com o id: %d não encontrado";


    @Autowired
    private ItemCardapioMapper itemCardapioMapper;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @Transactional
    public ItemCardapioDto salvarItemCardapio(ItemCardapioDto itemCardapioDto) {
        ItemCardapio itemCardapio = itemCardapioMapper.dtoToEntity(itemCardapioDto);
        return itemCardapioMapper.entityToDto(itemCardapioRepository.save(itemCardapio));
    }

    @Transactional
    public ItemCardapioDto atualizaCardapio(ItemCardapioDto itemCardapioDto, Long idCardapio) {
        ItemCardapio itemCardapioExistente = getCardapioByid(idCardapio);

        itemCardapioExistente.setNome(itemCardapioDto.getNome());
        itemCardapioExistente.setCaminhoFoto(itemCardapioDto.getCaminhoFoto());
        itemCardapioExistente.setDescricao(itemCardapioDto.getDescricao());
        itemCardapioExistente.setPreco(itemCardapioDto.getPreco());
        itemCardapioExistente.setDisponivelApenasNoLocal(itemCardapioDto.isDisponivelApenasNoLocal());

        return itemCardapioMapper.entityToDto(itemCardapioRepository.save(itemCardapioExistente));
    }

    public List<ItemCardapioDto> listarCardapio() {
        return itemCardapioMapper.entitiesToDto(itemCardapioRepository.findAll());
    }

    public void deletaCardapio(Long idCardapio) {
        itemCardapioRepository.delete(getCardapioByid(idCardapio));
    }

    public ItemCardapioDto getCardapio(Long idCardapio) {
        return itemCardapioMapper.entityToDto(getCardapioByid(idCardapio));
    }

    public ItemCardapio getCardapioByid(Long idCardapio) {
        return itemCardapioRepository.findById(idCardapio).orElseThrow(
                ()-> new UsuarioNotFoundException(String.format(CARDAPIO_NOT_FOUND_MESSAGE,idCardapio))
        );
    }

}
