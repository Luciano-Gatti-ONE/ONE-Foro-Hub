/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.forohub.api.domain.usuarios;

/**
 *
 * @author usuario
 */
public record DatosMostrarUsuario(
        Long id,
        String nombre,
        String correoElectronico,
        Boolean activo
) {
    public DatosMostrarUsuario(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                usuario.getActivo()
        );
    }
}
