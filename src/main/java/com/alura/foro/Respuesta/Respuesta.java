package com.alura.foro.Respuesta;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * este el modelo de respuesta, tiene 5 atributos
 */
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
    //@Column
    //@ManyToOne(fetch = FetchType.EAGER)
    //private Usuario author;
}
