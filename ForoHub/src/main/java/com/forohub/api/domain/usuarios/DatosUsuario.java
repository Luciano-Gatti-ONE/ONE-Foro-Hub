package com.forohub.api.domain.usuarios;

/**
 * DTO que representa los datos básicos de un usuario.
 * Contiene el identificador, nombre y correo electrónico.
 * Se puede construir a partir de una entidad Usuario.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosUsuario(
        Long id, 
        String nombre, 
        String correoElectronico
) {
    public DatosUsuario(Usuario usuario) {
        this(
                usuario.getId(), 
                usuario.getNombre(), 
                usuario.getCorreoElectronico()
            );
    }
}