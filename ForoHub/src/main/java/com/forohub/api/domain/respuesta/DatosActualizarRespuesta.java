package com.forohub.api.domain.respuesta;

import jakarta.validation.constraints.NotNull;

/**
 * DTO para actualizar una respuesta existente en el sistema.
 * Contiene el identificador de la respuesta y el nuevo mensaje que se desea asignar.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosActualizarRespuesta(
        @NotNull
        Long id,
        String mensaje 
) {}
