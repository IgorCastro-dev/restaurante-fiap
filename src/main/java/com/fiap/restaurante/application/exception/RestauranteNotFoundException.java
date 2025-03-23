package com.fiap.restaurante.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestauranteNotFoundException extends RuntimeException{
    public RestauranteNotFoundException(String message) {
        super(message);
    }
}
