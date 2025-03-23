package com.fiap.restaurante.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteResponseDto {
    @NotBlank(message = "O nome do restaurante é obrigatório")
    private String nome;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "O tipo de cozinha é obrigatório")
    private String tipoCozinha;

    @NotBlank(message = "O horário de funcionamento é obrigatório")
    private String horarioFuncionamento;

    @NotNull(message = "O Usuário dono do restaurante é obrigatório")
    private UsuarioSemSenhaDto usuarioDto;
}
