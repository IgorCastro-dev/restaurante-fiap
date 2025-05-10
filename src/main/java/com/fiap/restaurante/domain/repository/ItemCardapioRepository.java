package com.fiap.restaurante.domain.repository;

import com.fiap.restaurante.domain.entity.ItemCardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapio,Long> {
}
