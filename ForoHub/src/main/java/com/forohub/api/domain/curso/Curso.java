package com.forohub.api.domain.curso;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.forohub.api.domain.topico.Topico;

import java.util.List;

/**
* Representa un curso dentro del sistema.
* Cada curso tiene una única categoría, representada como un valor enum.
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
        this.categoria = Categoria.valueOf(datos.categoria().toUpperCase()); // Suponiendo que sea un enum
    }
}
