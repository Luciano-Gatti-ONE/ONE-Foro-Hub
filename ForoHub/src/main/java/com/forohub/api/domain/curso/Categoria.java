package com.forohub.api.domain.curso;

/**
 * Enum que representa las categorías disponibles para un curso dentro del sistema.
 * 
 * Cada categoría está asociada a una descripción legible que puede incluir espacios y mayúsculas,
 * pensada para mostrarla directamente en interfaces de usuario, reportes o documentación.
 * 
 * Este enum facilita la gestión y validación de categorías permitidas para los cursos,
 * asegurando consistencia en los valores utilizados a lo largo de la aplicación.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public enum Categoria {
    PROGRAMACION("PROGRAMACION"),
    FRONT_END("FRONT END"),
    DATA_SCIENCE("DATA SCIENCE"),
    INNOVACION_Y_GESTION("INNOVACION Y GESTION"),
    DEVOPS("DEVOPS"),
    OFF_TOPICS("OFF TOPICS");

    private final String descripcion;

    Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
