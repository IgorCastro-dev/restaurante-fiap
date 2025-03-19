package com.fiap.restaurante.util.mapper;

import com.fiap.restaurante.domain.dto.ItemCardapioDto;
import com.fiap.restaurante.domain.entity.ItemCardapio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemCardapioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ItemCardapio dtoToEntity(ItemCardapioDto itemCardapioDto){
        return modelMapper.map(itemCardapioDto, ItemCardapio.class);
    }

    public ItemCardapioDto entityToDto(ItemCardapio itemCardapio) {
        return modelMapper.map(itemCardapio, ItemCardapioDto.class);
    }
}
