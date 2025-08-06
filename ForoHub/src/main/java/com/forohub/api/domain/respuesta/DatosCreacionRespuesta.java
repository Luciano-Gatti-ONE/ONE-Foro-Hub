package com.forohub.api.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author usuario
 */
public record DatosCreacionRespuesta(
        @NotNull Long idAutor,
        @NotNull Long idTopico,
        @NotBlank String mensaje
) {}
