package com.forohub.api.domain.topico;

import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con datos resumidos de un tópico.
 * 
 * Contiene información básica del tópico junto con datos relevantes
 * del autor, curso y categoría asociada.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosRespuestaTopico(
    Long id,
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    String status,
    String autor,
    String curso,
    String categoria
) {
    public DatosRespuestaTopico(Topico topico) {
        this(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaDeCreacion(),
            topico.getStatus().getDescripcion(),
            topico.getAutor().getNombre(),
            topico.getCurso().getNombre(),
            topico.getCurso().getCategoria().getDescripcion()
        );
    }
}