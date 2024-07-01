package com.alura.foro.Respuesta;

import com.alura.foro.Usuario.Usuario;

/**
 * esto es un Dto de respuesta, es lo q envio en postman para q se cree una nueva resp en el foro
 * @param mensaje es el cuerpo de la respuesta el topico
 * @param solucion es un booleano si es verdadero esta resp es la solucion al topico
 */
public record RespuestaDto(String mensaje, boolean solucion) {
}
