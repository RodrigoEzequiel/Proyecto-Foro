package com.alura.foro.Topicos;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "topicos")
@Data
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String mensaje;

    @CreationTimestamp
    private Timestamp fechaCreacion;
    @UpdateTimestamp
    private Timestamp fechaActualizacion;
    //TODO hacer Status(enum), y Relacionar Author, Categoria y Respuestas

}