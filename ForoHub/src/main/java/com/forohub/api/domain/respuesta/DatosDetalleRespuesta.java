package com.forohub.api.domain.respuesta;

import java.time.LocalDateTime;

/**
 *
 * @author usuario
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
