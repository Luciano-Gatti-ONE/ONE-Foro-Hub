package com.forohub.api.domain.usuarios;

/**
 *
 * @author usuario
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
