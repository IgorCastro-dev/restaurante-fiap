package com.fiap.restaurante.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TipoUsuarioException extends RuntimeException{
    public TipoUsuarioException(String message){
        super(message);
    }
}
