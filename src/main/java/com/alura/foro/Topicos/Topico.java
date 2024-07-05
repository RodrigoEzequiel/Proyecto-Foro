package com.alura.foro.Topicos;

import com.alura.foro.Usuario.Usuario;
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
    private Long id_topico;

    @Column(nullable = false,unique = true)
    private String titulo;

    @Column(nullable = false)
    private String mensaje;

    @CreationTimestamp
    private Timestamp fechaCreacion;
    @UpdateTimestamp
    private Timestamp fechaActualizacion;
    //TODO hacer Status(enum), Categoria
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "id_author", nullable = false)
    private Usuario author;

}