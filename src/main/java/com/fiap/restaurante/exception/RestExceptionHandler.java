package com.fiap.restaurante.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<RestErroMessage> usuarioNaoEncontrado(UsuarioNotFoundException e){
        RestErroMessage restErroMessage = getRestErroMessage(HttpStatus.NOT_FOUND,e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErroMessage);
    }

    @ExceptionHandler(UsuarioAlreadyExistsException.class)
    public ResponseEntity<RestErroMessage> usuarioJaExiste(UsuarioAlreadyExistsException e){
        RestErroMessage restErroMessage = getRestErroMessage(HttpStatus.CONFLICT,e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(restErroMessage);
    }

    private static RestErroMessage getRestErroMessage(HttpStatus status, String message) {
        return RestErroMessage
                .builder()
                .httpStatus(status)
                .message(message)
                .build();
    }
}
