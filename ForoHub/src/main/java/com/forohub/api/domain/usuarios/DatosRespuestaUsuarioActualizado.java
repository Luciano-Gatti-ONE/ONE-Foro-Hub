package com.forohub.api.domain.usuarios;

import java.util.Map;

/**
 * DTO para enviar los datos actualizados de un usuario junto con avisos o mensajes adicionales.
 * Contiene el ID, nombre, correo electrónico y un mapa de avisos que pueden incluir mensajes
 * sobre validaciones o información relevante tras la actualización.
 * 
 * Se puede construir a partir de una entidad Usuario y un mapa de avisos.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosRespuestaUsuarioActualizado(
        Long id,
        String nombre,
        String correoElectronico,
        Map<String, String> avisos
) {
    public DatosRespuestaUsuarioActualizado(Usuario usuario, Map<String, String> avisos){
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                avisos
        );
    }
}