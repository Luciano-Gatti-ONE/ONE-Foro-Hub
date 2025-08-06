package com.forohub.api.domain.respuesta;

import java.time.LocalDateTime;
import java.util.Map;

/**
 *
 * @author usuario
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