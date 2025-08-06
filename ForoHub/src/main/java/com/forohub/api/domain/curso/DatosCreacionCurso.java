package com.forohub.api.domain.curso;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO inmutable que representa los datos necesarios para crear un nuevo curso.
 * 
 * Contiene el nombre y la categoría del curso, ambos campos son obligatorios
 * y no pueden estar en blanco.
 * 
 * Utilizado para validar y transportar la información recibida en solicitudes de creación de cursos.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosCreacionCurso(
        @NotBlank
        String nombre,
        @NotBlank
        String categoria
) {}
