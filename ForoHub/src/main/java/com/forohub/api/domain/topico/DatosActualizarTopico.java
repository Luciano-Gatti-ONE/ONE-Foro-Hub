package com.forohub.api.domain.topico;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author usuario
 */
public record DatosActualizarTopico(
        @NotNull
        Long id,
        String titulo,
        String mensaje,
        String status
){}
