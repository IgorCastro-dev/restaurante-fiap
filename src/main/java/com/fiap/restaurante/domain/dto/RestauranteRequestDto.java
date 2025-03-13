package com.fiap.restaurante.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestauranteRequestDto {
    @NotBlank(message = "O nome do restaurante é obrigatório")
    private String nome;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "O tipo de cozinha é obrigatório")
    private String tipoCozinha;

    @NotBlank(message = "O horário de funcionamento é obrigatório")
    private String horarioFuncionamento;

    @NotNull(message = "O ID do usuário dono do restaurante é obrigatório")
    private Long idUsuario;
}

