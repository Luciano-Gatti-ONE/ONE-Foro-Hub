package com.forohub.api.domain.topico;

import java.util.Map;

/**
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
