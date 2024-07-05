package com.alura.foro.Errores;

public class BadRequestException extends Exception{

    public BadRequestException(String s) {
        super(s);
    }
}
