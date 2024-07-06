package com.alura.foro.Respuesta;

/**
 * esto es un Dto de respuesta, es lo q envio en postman para q se cree una nueva resp en el foro
 * @param mensaje es el cuerpo de la respuesta el topico
 * @param id_topico es el id del topico q se esta respondiendo
 */
public record NewRespuestaDto(String mensaje, Long id_topico, String author) {
}
