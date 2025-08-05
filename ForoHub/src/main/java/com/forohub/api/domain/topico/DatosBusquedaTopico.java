package com.forohub.api.domain.topico;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

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
