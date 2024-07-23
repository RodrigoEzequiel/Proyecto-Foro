package com.alura.foro.Topicos;

import com.alura.foro.Categoria.Categoria;
import java.sql.Timestamp;

public record TopicoFullDto(Long idTopico,
                            String titulo,
                            String mensaje,
                            Timestamp fechaCreacion,
                            Timestamp fechaActualizacion,
                            String autorNombre,
                            String autorApellido,
                            String autorEmail,
                            TopicoStatus status,
                            Categoria categoria,
                            Long cantRespuestas) {
}
