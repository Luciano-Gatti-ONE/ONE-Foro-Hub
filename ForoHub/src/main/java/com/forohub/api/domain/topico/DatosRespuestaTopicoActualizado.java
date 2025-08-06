package com.forohub.api.domain.topico;

import java.util.Map;

/**
 * DTO que representa la respuesta tras actualizar un tópico.
 * 
 * Incluye los datos principales del tópico actualizado y un mapa de avisos
 * que puede contener mensajes relacionados con validaciones o actualizaciones parciales.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosRespuestaTopicoActualizado(
        Long id,
        String titulo,
        String mensaje,
        String status,
        Map<String, String> avisos
) {
    public DatosRespuestaTopicoActualizado(Topico topico, Map<String, String> avisos){
        this(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getStatus().getDescripcion(),
            avisos
        );
    }
}
