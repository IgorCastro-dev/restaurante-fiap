package com.fiap.restaurante.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {

        StringBuilder errorMessage = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String defaultMessage = error.getDefaultMessage();
            errorMessage.append(defaultMessage).append(". ");
        });

        RestErroMessage restErroMessage = getRestErroMessage(
                HttpStatus.BAD_REQUEST,
                errorMessage.toString().trim()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErroMessage);
    }


    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<RestErroMessage> usuarioNaoEncontrado(UsuarioNotFoundException e){
        RestErroMessage restErroMessage = getRestErroMessage(HttpStatus.NOT_FOUND,e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErroMessage);
    }

    @ExceptionHandler(CredencialErradoException.class)
    public ResponseEntity<RestErroMessage> senhaErradaException(CredencialErradoException e){
        RestErroMessage restErroMessage = getRestErroMessage(HttpStatus.UNAUTHORIZED,e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(restErroMessage);
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
