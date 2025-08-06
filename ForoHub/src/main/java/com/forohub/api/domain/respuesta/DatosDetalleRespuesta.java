package com.forohub.api.domain.respuesta;

import java.time.LocalDateTime;

/**
 * DTO que representa el detalle completo de una respuesta en el foro.
 * Incluye el ID, mensaje, fecha de creación, nombre del autor y título del tópico asociado.
 * 
 * Se utiliza para enviar información detallada sobre una respuesta en las APIs REST.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        String nombreAutor,
        String nombreTopico        
) {
    public DatosDetalleRespuesta(Respuesta respuesta){
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor().getNombre(),
                respuesta.getTopico().getTitulo()
        );
    }
}
