package com.forohub.api.domain.topico;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author usuario
 */
public record DatosCreacionTopico(
        @NotNull
        Long idAutor,
        @NotNull
        Long idCurso,
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje
) {}
