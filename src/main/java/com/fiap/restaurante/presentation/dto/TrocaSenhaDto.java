package com.fiap.restaurante.presentation.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class TrocaSenhaDto {
    @NotBlank(message = "A senha atual é obrigatório")
    private String senhaAtual;
    @NotBlank(message = "A nova senha é obrigatório")
    private String novaSenha;
    @NotBlank(message = "A confirmação de senha é obrigatório")
    private String confirmaSenha;
}