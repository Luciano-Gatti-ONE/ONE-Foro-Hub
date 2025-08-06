package com.forohub.api.domain.respuesta;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author usuario
 */
public record DatosActualizarRespuesta(
        @NotNull
        Long id,
        String mensaje 
) {}
