package com.forohub.api.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para la creación de una nueva respuesta en el foro.
 * Contiene el ID del autor, el ID del tópico al que pertenece y el mensaje de la respuesta.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosCreacionRespuesta(
        @NotNull 
        Long idAutor,
        @NotNull 
        Long idTopico,
        @NotBlank 
        String mensaje
) {}
