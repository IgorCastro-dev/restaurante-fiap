package com.fiap.restaurante.domain.repository;

import com.fiap.restaurante.domain.entity.Usuario;
import com.fiap.restaurante.domain.services.AuthenticationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmailOrLogin(String email, String login);

    Optional<Usuario> findByLogin(String login);

}
