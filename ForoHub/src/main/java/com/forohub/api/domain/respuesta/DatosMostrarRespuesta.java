package com.forohub.api.domain.respuesta;

import java.time.LocalDateTime;

/**
 *
 * @author usuario
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
