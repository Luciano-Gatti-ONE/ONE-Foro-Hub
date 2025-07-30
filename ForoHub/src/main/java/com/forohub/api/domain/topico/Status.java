package com.forohub.api.domain.topico;

/**
 * Enum que representa el estado de un tópico.
 * Puede estar sin respuesta, en curso o resuelto.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public enum Status {
    SIN_RESPUESTA("SIN RESPUESTA"),
    EN_CURSO("EN CURSO"),
    RESUELTO("RESUELTO");

    private final String descripcion;

    Status(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}