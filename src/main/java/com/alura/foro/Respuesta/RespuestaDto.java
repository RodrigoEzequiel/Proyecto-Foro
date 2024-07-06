package com.alura.foro.Respuesta;

import com.alura.foro.Topicos.TopicoDto;

import java.sql.Timestamp;

public record RespuestaDto(Long id_respuesta,
                           String mensaje,
                           Timestamp fechaCreacion,
                           Timestamp fechaActualizacion,
                           Boolean solucion,
                           TopicoDto topico,
                           String authorEmail,
                           String authorNombre,
                           String authorApellido) {

    public static RespuestaDto convertirRespuestaEnDto(Respuesta respuesta){
        return new RespuestaDto(respuesta.getId_respuesta(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getFechaActualizacion(),
                respuesta.isSolucion(),
                TopicoDto.convertirTopicoEnDto(respuesta.getTopico()),
                respuesta.getAuthor().getLogin(),
                respuesta.getAuthor().getNombre(),
                respuesta.getAuthor().getApellido());
    }
}
