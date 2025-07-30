/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.forohub.api.domain.topico;

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
                topico.getFechaCreacion(), topico.getStatus().getDescripcion(), 
                topico.getAutor().getNombre(), topico.getCurso().getNombre()
            );
    }
}