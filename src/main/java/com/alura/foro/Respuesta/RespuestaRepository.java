package com.alura.foro.Respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {
    @Query(value = "SELECT r FROM Respuesta r WHERE r.topico.id_topico =:id ")
    Page<Respuesta> listarRespuestaPorTopicoId(Long id, Pageable pagina);

    @Query(value = "SELECT r FROM Respuesta r WHERE r.topico.id_topico =:id ")
    List<Respuesta> listarRespuestaPorTopicoId(Long id);
}
