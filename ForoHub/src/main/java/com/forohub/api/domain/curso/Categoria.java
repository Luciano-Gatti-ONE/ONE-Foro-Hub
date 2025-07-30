package com.forohub.api.domain.curso;

/**
 * Enum que representa las categorías disponibles para un curso.
 * Cada categoría tiene una descripción asociada que puede contener espacios y mayúsculas.
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
