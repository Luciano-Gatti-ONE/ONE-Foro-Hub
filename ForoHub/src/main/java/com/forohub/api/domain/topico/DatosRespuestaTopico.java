package com.forohub.api.domain.topico;

import java.time.LocalDateTime;

/**
 *
 * @author usuario
 */
public record DatosRespuestaTopico(Long id, String mensaje, String titulo, 
        LocalDateTime fechaCreacion, String status, String autor, String curso
        ) 
{
    public DatosRespuestaTopico(Topico topico) {
        this(topico.getId(), topico.getMensaje(), topico.getTitulo(), 
                topico.getFechaDeCreacion(), topico.getStatus().getDescripcion(), 
                topico.getAutor().getNombre(), topico.getCurso().getNombre()
            );
    }
}