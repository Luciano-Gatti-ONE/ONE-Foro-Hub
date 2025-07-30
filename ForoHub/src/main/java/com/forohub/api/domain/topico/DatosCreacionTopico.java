/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.forohub.api.domain.topico;

/**
 *
 * @author usuario
 */
public record DatosCreacionTopico(
        @NotNull
        Long idAutor,
        @NotNull
        Long idCurso,
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje
) {}
