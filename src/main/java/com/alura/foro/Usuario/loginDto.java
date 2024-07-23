package com.alura.foro.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record loginDto(@NotBlank @Email String login,
                       @NotBlank @Size(min= 8, max = 256)String clave) {
}
