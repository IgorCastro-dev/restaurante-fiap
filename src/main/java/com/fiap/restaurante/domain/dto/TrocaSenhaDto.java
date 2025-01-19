package com.fiap.restaurante.domain.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TrocaSenhaDto {
    @NotBlank(message = "A senha atual é obrigatório")
    private String senhaAtual;

    @NotBlank(message = "A nova senha é obrigatório")
    private String novaSenha;

    @NotBlank(message = "A confirmação de senha é obrigatório")
    private String confirmaSenha;
}