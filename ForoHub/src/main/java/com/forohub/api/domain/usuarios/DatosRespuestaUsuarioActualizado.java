package com.forohub.api.domain.usuarios;

import java.util.Map;

/**
 *
 * @author usuario
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