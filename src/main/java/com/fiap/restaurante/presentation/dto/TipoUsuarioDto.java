package com.fiap.restaurante.presentation.dto;

import com.fiap.restaurante.domain.entity.TipoUsuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TipoUsuarioDto {

    @NotBlank(message = "O tipo de usuario é obrigatório")
    private String tipoUsuario;
}
