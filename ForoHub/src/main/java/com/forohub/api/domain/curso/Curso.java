package com.forohub.api.domain.curso;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.forohub.api.domain.topico.Topico;

import java.util.List;

/**
 * Representa un curso dentro del sistema.
 * Cada curso tiene un identificador único, un nombre y una categoría.
 * La categoría está representada como un valor enumerado (enum) `Categoria`.
 * 
 * También mantiene la lista de tópicos asociados a este curso.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    
    @OneToMany(mappedBy = "curso", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Topico> topicos;
    
    public Curso(DatosCreacionCurso datos) {
        this.nombre = datos.nombre();
        this.categoria = Categoria.valueOf(normalizar(datos.categoria()));
    }

    private String normalizar(String dato){
        return dato.trim().replace(" ", "_").toUpperCase();
    }
}
