/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.forohub.api.domain.usuarios;

/**
 *
 * @author usuario
 */
public record DatosUsuario(
        Long id, String nombre, String correoElectronico
) {
    public DatosUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), 
                usuario.getCorreoElectronico()
        );
    }
}
