package com.alura.foro.Respuesta;

import java.sql.Timestamp;

public record RespuestaDto(Long id_respuesta,
                           String mensaje,
                           Timestamp fechaCreacion,
                           Timestamp fechaActualizacion,
                           Boolean solucion,
                           String authorEmail,
                           String authorNombre,
                           String authorApellido) {

    public static RespuestaDto convertirRespuestaEnDto(Respuesta respuesta){
        return new RespuestaDto(respuesta.getId_respuesta(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getFechaActualizacion(),
                respuesta.isSolucion(),
                respuesta.getAuthor().getLogin(),
                respuesta.getAuthor().getNombre(),
                respuesta.getAuthor().getApellido());
    }
}
