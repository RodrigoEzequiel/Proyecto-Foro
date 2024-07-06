package com.alura.foro.Topicos;

import com.alura.foro.Categoria.Categoria;

import java.sql.Timestamp;

public record TopicoDto(Long id_topico,
                        String titulo,
                        String mensaje,
                        Timestamp fechaCreacion,
                        Timestamp fechaActualizacion,
                        String authorEmail,
                        String authorNombre,
                        String authorApellido,
                        TopicoStatus status,
                        Categoria categoria) {
    public static  TopicoDto convertirTopicoEnDto(Topico topico){
        return new TopicoDto(topico.getId_topico(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getFechaActualizacion(),
                topico.getAuthor().getLogin(),
                topico.getAuthor().getNombre(),
                topico.getAuthor().getApellido(),
                topico.getStatus(),
                topico.getCategoria());
    }
}
