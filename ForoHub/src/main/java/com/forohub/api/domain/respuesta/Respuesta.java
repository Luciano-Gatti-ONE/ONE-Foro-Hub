package com.forohub.api.domain.respuesta;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import com.forohub.api.domain.topico.Topico;
import com.forohub.api.domain.usuarios.Usuario;

/**
 * Representa una respuesta a un tópico dentro del sistema.
 * Contiene el mensaje, el tópico asociado, la fecha de creación,
 * el autor de la respuesta, un indicador de si la respuesta está activa,
 * y si dicha respuesta ha sido marcada como solución del tópico.
 * 
 * Proporciona constructores para creación y métodos para desactivar
 * la respuesta y marcarla como solución.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;
    
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    private Boolean solucion;
    
    public Respuesta(String mensaje, Usuario autor, Topico topico){
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.topico = topico;
        this.autor = autor;
        this.solucion = false;
        this.activo = true;
    }
    
    /**
     * Marca la respuesta como inactiva (borrado lógico).
     */
    public void desactivarRespuesta(){
        this.activo = false;
    }
    
    /**
     * Marca esta respuesta como la solución del tópico.
     */
    public void marcarSolucion(){
        this.solucion = true;
    }
}