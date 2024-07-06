package com.alura.foro.Categoria;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categorias")
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id_categoria;

    @Column(nullable = false,length = 100)
    private String nombre;
}
