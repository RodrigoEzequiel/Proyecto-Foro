package com.alura.foro.Topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateTopicoDto(@NotBlank @Size(min= 10, max = 256)String titulo,
                              @NotBlank @Size(min= 10, max = 256)String mensaje) {
}
