package com.alura.foro.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUserDto(@Email String email,
                         @NotBlank @Size(min = 8, max = 256) String password,
                         @NotBlank String nombre,
                         @NotBlank String apellido) {
}
