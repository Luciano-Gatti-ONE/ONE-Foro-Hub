package com.forohub.api.domain.usuarios;

/**
 *
 * @author usuario
 */
public record DatosMostrarUsuario(
        Long id,
        String nombre,
        String correoElectronico
) {
    public DatosMostrarUsuario(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico()
        );
    }
}
