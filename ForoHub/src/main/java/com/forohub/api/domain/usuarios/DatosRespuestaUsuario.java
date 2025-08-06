package com.forohub.api.domain.usuarios;

/**
 * DTO para enviar datos de respuesta sobre un usuario.
 * Contiene el ID, nombre y correo electrónico del usuario.
 * 
 * Se puede construir a partir de una entidad Usuario.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosRespuestaUsuario(
        Long id,
        String nombre,
        String correoElectronico
) {
    public DatosRespuestaUsuario(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico()
        );
    }
}