package com.alura.foro.Topicos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico,Long> {
    @Query(value=
            "SELECT new com.alura.foro.Topicos.TopicoFullDto(" +
                    "t.id_topico AS idTopico, " +
                    "t.titulo AS titulo, " +
                    "t.mensaje AS mensaje, " +
                    "t.fechaCreacion AS fechaCreacion, " +
                    "t.fechaActualizacion AS fechaActualizacion, " +
                    "t.author.nombre AS autorNombre, " +
                    "t.author.apellido AS authorApellido, " +
                    "t.author.login AS authorEmail, " +
                    "t.status AS status, " +
                    "t.categoria AS categoria, " +
                    "COUNT(r.id_respuesta) AS cantRespuestas) " +
                    "FROM Topico t " +
                    "LEFT JOIN Respuesta r " +
                    "ON t.id_topico = r.topico.id_topico " +
                    "GROUP BY t.id_topico " +
                    "ORDER BY t.fechaActualizacion DESC")
    Page<TopicoFullDto> listarTopicosComoDto(Pageable pagina);
}
