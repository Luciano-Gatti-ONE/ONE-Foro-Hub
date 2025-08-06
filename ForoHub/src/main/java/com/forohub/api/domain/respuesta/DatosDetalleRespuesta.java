/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.forohub.api.domain.respuesta;

/**
 *
 * @author usuario
 */
public record DatosDetalleRespuesta(
        String mensaje,
        LocalDateTime fechaCreacion,
        String nombreAutor,
        String nombreTopico        
) {
    public DatosDetalleRespuesta(Respuesta respuesta){
        this(
                respuesta.getMensaje(),
                respuesta.getFechaCreacion,
                respuesta.getAutor().getNombre(),
                respuesta.getTopico().getTitulo()
        );
    }
}
