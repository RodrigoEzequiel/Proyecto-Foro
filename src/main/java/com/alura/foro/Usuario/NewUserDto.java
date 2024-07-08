package com.alura.foro.Usuario;

public record NewUserDto(String email,
                         String password,
                         String nombre,
                         String apellido) {
}
