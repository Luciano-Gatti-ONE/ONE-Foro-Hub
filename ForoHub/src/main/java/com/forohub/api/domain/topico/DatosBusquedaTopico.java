package com.forohub.api.domain.topico;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para realizar búsquedas de tópicos filtrados por curso y año.
 * 
 * Contiene el nombre del curso y el año en que se crearon o asignaron los tópicos.
 * Los campos son obligatorios para realizar la búsqueda.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosBusquedaTopico(
        @NotBlank(message = "El nombre del curso es obligatorio.")
        String nombreCurso,
        @NotNull(message = "El año es obligatorio.")
        Long añoTopico
) {}
