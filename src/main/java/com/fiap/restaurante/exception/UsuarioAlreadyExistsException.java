package com.fiap.restaurante.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsuarioAlreadyExistsException extends RuntimeException{
    public UsuarioAlreadyExistsException(String message){
        super(message);
    }
}
