package com.fiap.br.globalSolution.application.errors;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
