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
 * Contiene un título, un mensaje, una fecha de creación, el estado del tópico,
 * y las relaciones con el autor, el curso al que pertenece y las respuestas asociadas.
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
    
    //SE UTILIZA EN EL CASO DE ELIMINACION LOGICA
    //private Boolean activo;

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
    
    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaDeCreacion = LocalDateTime.now();
        this.status = Status.SIN_RESPUESTAS;
        this.autor = autor;
        this.curso = curso;
    }
    
    /**
    * SE UTILIZA EN EL CASO DE ELIMINACION LOGICA
    * public void desactivarTopico() {   
    *       this.activo = false;
    * }
    */
    
}