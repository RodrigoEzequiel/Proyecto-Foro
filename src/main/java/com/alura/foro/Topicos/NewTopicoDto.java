package com.alura.foro.Topicos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewTopicoDto(@NotBlank @Size(min= 10, max = 256)String titulo,
                           @NotBlank @Size(min= 10, max = 256) String mensaje,
                           @Email String author,
                           @Min(1) Long id_categoria) {
}
