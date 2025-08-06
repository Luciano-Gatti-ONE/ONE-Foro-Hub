package com.forohub.api.domain.curso;

/**
 * DTO inmutable que representa la respuesta con los datos de un curso.
 * 
 * Se utiliza para enviar información del curso en respuestas de la API.
 * 
 * Contiene el ID, nombre y la descripción de la categoría del curso.
 * 
 * Incluye un constructor que facilita la conversión desde la entidad Curso.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosRespuestaCurso(
        Long id, 
        String nombre, 
        String categoria
) {
    public DatosRespuestaCurso(Curso curso) {
        this(
                curso.getId(), 
                curso.getNombre(), 
                curso.getCategoria().getDescripcion()
        );
    }
}
