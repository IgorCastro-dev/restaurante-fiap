package com.fiap.restaurante.services;

import com.fiap.restaurante.domain.entity.TipoUsuario;
import com.fiap.restaurante.domain.entity.Usuario;
import com.fiap.restaurante.application.services.TipoUsuarioService;
import com.fiap.restaurante.application.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TipoUsuarioServiceTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private TipoUsuarioService tipoUsuarioService;

    @Test
    void givenTipoUsuarioValues_whenListar_thenReturnListOfDescricoes() {
        // Arrange
        List<String> expectedDescricoes = List.of("Cliente", "Dono de Restaurante");

        // Act
        List<String> result = tipoUsuarioService.listar();

        // Assert
        assertEquals(expectedDescricoes, result); // Verifica se a lista de descrições está correta
    }

    @Test
    void givenValidIdUsuarioAndTipoUsuario_whenAssociaTipoUsuario_thenUpdateUsuario() {
        // Arrange
        Integer idUsuario = 1;
        String tipoUsuario = "Dono de Restaurante";
        Usuario usuario = new Usuario();
        usuario.setIdUsusario(idUsuario);

        when(usuarioService.getUsuarioByid(idUsuario)).thenReturn(usuario);

        // Act
        tipoUsuarioService.associaTipoUsuario(idUsuario, tipoUsuario);

        // Assert
        assertEquals(TipoUsuario.DONO_DE_RESTAURANTE, usuario.getTipoUsuario()); // Verifica se o tipo de usuário foi atualizado
        verify(usuarioService, times(1)).getUsuarioByid(idUsuario); // Verifica se o método getUsuarioByid foi chamado
    }
}
