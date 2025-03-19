package com.fiap.restaurante.domain.services;

import com.fiap.restaurante.domain.dto.ItemCardapioDto;
import com.fiap.restaurante.domain.dto.UsuarioDto;
import com.fiap.restaurante.domain.dto.UsuarioSemSenhaDto;
import com.fiap.restaurante.domain.entity.ItemCardapio;
import com.fiap.restaurante.domain.entity.Usuario;
import com.fiap.restaurante.domain.repository.ItemCardapioRepository;
import com.fiap.restaurante.exception.UsuarioAlreadyExistsException;
import com.fiap.restaurante.util.mapper.ItemCardapioMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCardapioService {

    @Autowired
    private ItemCardapioMapper itemCardapioMapper;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @Transactional
    public ItemCardapioDto salvarItemCardapio(ItemCardapioDto itemCardapioDto) {
        ItemCardapio itemCardapio = itemCardapioMapper.dtoToEntity(itemCardapioDto);
        return itemCardapioMapper.entityToDto(itemCardapioRepository.save(itemCardapio));
    }

}
