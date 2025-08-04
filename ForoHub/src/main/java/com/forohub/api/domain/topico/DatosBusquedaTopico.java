/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.forohub.api.domain.topico;

/**
 *
 * @author lucia
 */
public record DatosBusquedaTopico(
        @NotBlank(message = "El nombre del curso es obligatorio.")
        String nombreCurso,
        @NotNull (message = "El año es obligatorio.")
        Long añoTopico
) {}
