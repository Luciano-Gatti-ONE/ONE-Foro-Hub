package com.forohub.api.domain.respuesta;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import com.forohub.api.domain.topico.Topico;
import com.forohub.api.domain.usuarios.Usuario;

/**
* Representa una respuesta a un tópico dentro del sistema.
* Contiene el mensaje, el tópico al que pertenece, la fecha de creación,
* el autor de la respuesta y si la respuesta es la solución al tópico.
* 
* @author Luciano Emmanuel Gatti Flekenstein
*/

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    private Boolean solucion;
}
