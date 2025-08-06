package com.forohub.api.domain.curso;

/**
 * DTO inmutable que representa los datos de un curso para mostrar o transferir.
 * 
 * Contiene el identificador, nombre y la categoría (como texto descriptivo) del curso.
 * 
 * Proporciona un constructor que recibe una entidad Curso para facilitar la conversión.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosCurso(
        Long id,
        String nombre,
        String categoria
) {
    public DatosCurso(Curso curso) {
        this(
                curso.getId(), 
                curso.getNombre(), 
                curso.getCategoria().getDescripcion()
        );
    }
}