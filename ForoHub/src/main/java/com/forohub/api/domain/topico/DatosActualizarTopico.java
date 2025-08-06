package com.forohub.api.domain.topico;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para actualizar un tópico existente.
 * 
 * Contiene el identificador del tópico y los campos que pueden ser modificados:
 * título, mensaje y estado (status).
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosActualizarTopico(
        @NotNull
        Long id,
        String titulo,
        String mensaje,
        String status
) {}