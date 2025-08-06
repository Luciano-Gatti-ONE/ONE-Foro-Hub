package com.forohub.api.domain.topico;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para la creación de un nuevo tópico en el foro.
 * 
 * Contiene el identificador del autor, el identificador del curso al que pertenece,
 * así como el título y el mensaje del tópico.
 * 
 * Todos los campos son obligatorios.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
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
