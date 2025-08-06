package com.forohub.api.domain.topico;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.forohub.api.domain.usuarios.Usuario;
import com.forohub.api.domain.curso.Curso;
import com.forohub.api.domain.respuesta.Respuesta;

/**
 * Representa un tópico o pregunta dentro del sistema.
 * 
 * Contiene un título, un mensaje, la fecha de creación, el estado actual del tópico,
 * y las relaciones con el autor que lo creó, el curso al que pertenece y las respuestas asociadas.
 * 
 * La entidad está mapeada para persistirse en la tabla "topicos".
 * 
 * El atributo `status` indica si el tópico está sin respuestas, en curso o resuelto.
 * 
 * Se contempla la posibilidad de realizar borrado lógico mediante el campo `activo` (comentado actualmente).
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    private LocalDateTime fechaDeCreacion;

    // Atributo para borrado lógico, actualmente no utilizado
    // private Boolean activo;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas;

    /**
     * Constructor para crear un tópico nuevo con estado inicial SIN_RESPUESTAS y fecha actual.
     * 
     * @param titulo título del tópico
     * @param mensaje contenido o descripción del tópico
     * @param autor usuario que crea el tópico
     * @param curso curso al que está asociado el tópico
     */
    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaDeCreacion = LocalDateTime.now();
        this.status = Status.SIN_RESPUESTAS;
        this.autor = autor;
        this.curso = curso;
    }

    /**
     * Método para realizar borrado lógico del tópico.
     * Actualmente está comentado ya que no se utiliza.
     * 
     * public void desactivarTopico() {
     *      this.activo = false;
     * }
     */
}