package com.alura.foro.Respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateRespuestaDto(@NotBlank @Size(min= 10, max = 256)String mensaje) {
}
