/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.forohub.api.domain.curso;

/**
 *
 * @author usuario
 */
public record DatosCurso(
        Long id,
        String nombre,
        String categoria
) {
    public DatosCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), 
                curso.getCategoria().getDescripcion()
        );
    }
}
