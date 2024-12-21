package com.fiap.restaurante.domain.dto;

import com.fiap.restaurante.domain.entity.TipoUsuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioSemSenhaDto {
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    private String nome;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;

    @NotBlank(message = "O login é obrigatório")
    private String login;

    private String endereco;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
}
