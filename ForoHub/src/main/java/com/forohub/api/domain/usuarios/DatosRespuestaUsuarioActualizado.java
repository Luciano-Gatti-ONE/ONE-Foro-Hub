/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.forohub.api.domain.usuarios;

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
    public DatosRespuestaUsuario(Usuario usuario, Map<String, String> avisos){
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                avisos
        );
    }
}