package com.forohub.api.domain.topico;

/**
 * Enum que representa los posibles estados de un tópico en el foro.
 * 
 * Los estados disponibles son:
 * - SIN_RESPUESTAS: El tópico no tiene respuestas aún.
 * - EN_CURSO: El tópico está activo y en discusión.
 * - RESUELTO: El tópico ha sido resuelto o cerrado.
 * 
 * Cada estado tiene una descripción asociada que se puede utilizar para mostrar
 * un texto legible en la interfaz de usuario.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public enum Status {
    SIN_RESPUESTAS("SIN RESPUESTA"),
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