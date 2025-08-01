package com.forohub.api.domain.topico;

import java.time.LocalDateTime;

/**
 *
 * @author usuario
 */
public record DatosMostrarTopico(Long id, String titulo, String mensaje, 
        LocalDateTime fechaDeCreacion, String status, Long idAutor, String nombreAutor,
        String correoAutor, Long cursoId, String nombreCurso
) {
    public DatosMostrarTopico(Topico topico){
        this(
            topico.getId(), topico.getTitulo(), topico.getMensaje(),
            topico.getFechaDeCreacion(), topico.getStatus().getDescripcion(), 
            topico.getAutor().getId(), topico.getAutor().getNombre(), 
            topico.getAutor().getCorreoElectronico(), topico.getCurso().getId(),
            topico.getCurso().getNombre()
        );
    }
}
