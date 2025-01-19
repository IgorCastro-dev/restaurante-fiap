package com.fiap.restaurante.exception;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class RestErroMessage {
    private HttpStatus httpStatus;
    private String message;
}
