package com.forohub.api.domain.respuesta;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO que representa el detalle de una respuesta actualizada en el foro.
 * Incluye el ID, mensaje, fecha de creación, nombre del autor, título del tópico asociado,
 * y un mapa de avisos que puede contener mensajes adicionales relacionados con la actualización.
 * 
 * Se utiliza para enviar información detallada junto con posibles notificaciones o advertencias
 * tras actualizar una respuesta mediante la API REST.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosDetalleRespuestaActualizada(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        String nombreAutor,
        String nombreTopico,
        Map<String, String> avisos
) {
    public DatosDetalleRespuestaActualizada(Respuesta respuesta, Map<String, String> avisos){
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor().getNombre(),
                respuesta.getTopico().getTitulo(),
                avisos
        );
    }
}