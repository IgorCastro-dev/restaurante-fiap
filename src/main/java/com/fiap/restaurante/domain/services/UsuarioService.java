package com.fiap.restaurante.domain.services;

import com.fiap.restaurante.domain.dto.UsuarioSemSenhaDto;
import com.fiap.restaurante.domain.repository.UsuarioRepository;
import com.fiap.restaurante.domain.dto.UsuarioDto;
import com.fiap.restaurante.domain.entity.Usuario;
import com.fiap.restaurante.exception.CredencialErradoException;
import com.fiap.restaurante.exception.UsuarioAlreadyExistsException;
import com.fiap.restaurante.exception.UsuarioNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fiap.restaurante.util.Mapper.UsuarioMapper;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UsuarioService implements UserDetailsService {
    private static final String USER_NOT_FOUND_MESSAGE = "Usuário com o id: %d não encontrado";
    private static final String LOGIN_NOT_FOUND_MESSAGE = "Credenciais inválidas.";
    private static final String USER_ALREADY_EXISTS_MESSAGE = "Já existe um usuario com o login: %s ou email: %s cadastrado";
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<UsuarioDto> listaUsuario() {
        return usuarioMapper.entitiesToDtos(usuarioRepository.findAll());
    }

    @Transactional
    public UsuarioSemSenhaDto salvarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioMapper.dtoToEntity(usuarioDto);
        String usuarioDtoEmail = usuarioDto.getEmail();
        String usuarioDtoLogin = usuarioDto.getLogin();
        boolean usuarioJaExiste = usuarioRepository
                .findByEmailOrLogin(usuarioDtoEmail,usuarioDtoLogin)
                .isPresent();
        if(usuarioJaExiste){
            throw new UsuarioAlreadyExistsException(String.format(USER_ALREADY_EXISTS_MESSAGE,usuarioDtoLogin,usuarioDtoEmail));
        }
        usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        return usuarioMapper.entityToSemSenhaDto(usuarioRepository.save(usuario));
    }

    public UsuarioDto getUsuario(Integer idUsuario) {
        return usuarioMapper.entityToDto(getUsuarioByid(idUsuario));
    }


    public void deletaUsuario(Integer idUsuario) {
        usuarioRepository.delete(getUsuarioByid(idUsuario));
    }

    public UsuarioSemSenhaDto atualizaUsuario(UsuarioSemSenhaDto usuarioDto, Integer idUsuario) {
        Usuario usuarioExistente = getUsuarioByid(idUsuario);

        usuarioExistente.setNome(usuarioDto.getNome());
        usuarioExistente.setEmail(usuarioDto.getEmail());
        usuarioExistente.setEndereco(usuarioDto.getEndereco());
        usuarioExistente.setDataAlteracao(LocalDateTime.now());

        return usuarioMapper.entityToSemSenhaDto(usuarioRepository.save(usuarioExistente));
    }

    private Usuario getUsuarioByid(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario).orElseThrow(
                ()-> new UsuarioNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,idUsuario))
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username).orElseThrow(
                ()-> new CredencialErradoException(LOGIN_NOT_FOUND_MESSAGE)
        );
    }
}
