package com.forohub.api.domain.respuesta;

import java.time.LocalDateTime;

/**
 * DTO para mostrar información básica de una respuesta en el foro.
 * Incluye el mensaje, la fecha de creación, el nombre del tópico asociado
 * y el nombre del autor de la respuesta.
 * 
 * Se utiliza para listar respuestas en la interfaz o API de forma resumida.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosMostrarRespuesta(
        String mensaje,
        LocalDateTime fechaCreacion,
        String nombreTopico,
        String nombreAutor
) {
    public DatosMostrarRespuesta(Respuesta respuesta){
        this(
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getTopico().getTitulo(),
                respuesta.getAutor().getNombre()
        );
    }
}