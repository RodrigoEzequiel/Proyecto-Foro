package com.alura.foro.Respuesta;

import com.alura.foro.Usuario.Usuario;

/**
 * esto es un Dto de respuesta, es lo q envio en postman para q se cree una nueva resp en el foro
 * @param mensaje es el cuerpo de la respuesta el topico
 * @param id_topico es el id del topico q se esta respondiendo
 */
public record RespuestaDto(String mensaje, Long id_topico,String author) {
}
