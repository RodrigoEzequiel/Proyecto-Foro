package com.alura.foro.Respuesta;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * esto es un Dto de respuesta, es lo q envio en postman para q se cree una nueva resp en el foro
 * @param mensaje es el cuerpo de la respuesta el topico
 * @param id_topico es el id del topico q se esta respondiendo
 */
public record NewRespuestaDto(@NotBlank @Size(min= 10, max = 256) String mensaje,
                              @Min(1) Long id_topico,
                              @NotBlank @Email String author) {
}
