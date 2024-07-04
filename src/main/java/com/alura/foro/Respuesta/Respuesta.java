package com.alura.foro.Respuesta;


import com.alura.foro.Topicos.Topico;
import com.alura.foro.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;

@Table(name = "respuestas")
@Entity
@Data
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_respuesta;

    @Column(nullable = false)
    private String mensaje;
    @CreationTimestamp
    private Timestamp fechaCreacion;
    @UpdateTimestamp
    private Timestamp fechaActualizacion;
    @Column(columnDefinition="tinyint(1) default 1")
    private boolean solucion;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_topico", nullable = false)
    private Topico topico;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_author", nullable = false)
    private Usuario author;
}
