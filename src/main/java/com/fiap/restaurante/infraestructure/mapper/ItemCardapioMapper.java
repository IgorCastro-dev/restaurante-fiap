package com.fiap.restaurante.infraestructure.mapper;

import com.fiap.restaurante.presentation.dto.ItemCardapioDto;
import com.fiap.restaurante.domain.entity.ItemCardapio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ItemCardapioDto> entitiesToDto(List<ItemCardapio> itensCardapio) {
        return itensCardapio.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
