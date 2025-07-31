package com.forohub.api.domain.curso;

import jakarta.validation.constraints.NotBlank;

/**
 *
 * @author usuario
 */
public record DatosCreacionCurso(
        @NotBlank
        String nombre,
        @NotBlank
        String categoria
) {}
