package com.fiap.restaurante.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public LoginDto(String username, String password) {
    }
}
