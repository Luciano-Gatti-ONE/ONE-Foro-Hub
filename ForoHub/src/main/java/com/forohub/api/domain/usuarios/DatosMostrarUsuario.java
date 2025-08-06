package com.forohub.api.domain.usuarios;

/**
 * DTO para mostrar los datos públicos de un usuario.
 * Contiene el identificador, el nombre y el correo electrónico del usuario.
 * 
 * Se utiliza para responder consultas donde no se expone información sensible.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
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