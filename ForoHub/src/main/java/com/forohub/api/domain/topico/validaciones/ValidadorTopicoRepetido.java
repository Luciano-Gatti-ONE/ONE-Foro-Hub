package com.forohub.api.domain.topico.validaciones;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.forohub.api.domain.topico.DatosCreacionTopico;
import com.forohub.api.domain.topico.DatosActualizarTopico;
import com.forohub.api.domain.topico.TopicoRepository;
import com.forohub.api.domain.ValidacionException;

/**
 * Validador que verifica que no existan tópicos con título y mensaje duplicados.
 * Implementa la interfaz ValidadorDeTopicos para validar en creación y actualización.
 * 
 * En creación, valida que no exista ningún tópico con mismo título y mensaje.
 * En actualización, valida que no exista otro tópico diferente con mismo título y mensaje.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Component
public class ValidadorTopicoRepetido implements ValidadorDeTopicos {

    @Autowired
    private TopicoRepository repository;

    /**
     * Valida que no exista un tópico con el mismo título y mensaje antes de crear uno nuevo.
     * 
     * @param datos DTO con datos para crear el tópico.
     * @throws ValidacionException si ya existe un tópico con título y mensaje idénticos.
     */
    @Override
    public void validarCreacion(DatosCreacionTopico datos) {
        var topico = repository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (topico.isPresent()) {
            throw new ValidacionException("Ya existe un tópico con ese título y mensaje.");
        }
    }

    /**
     * Valida que al actualizar un tópico no se duplique el título y mensaje de otro tópico distinto.
     * 
     * @param datos DTO con datos para actualizar el tópico.
     * @throws ValidacionException si otro tópico diferente ya tiene el mismo título y mensaje.
     */
    @Override
    public void validarActualizacion(DatosActualizarTopico datos) {
        var topicoOpt = repository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());

        if (topicoOpt.isPresent()) {
            var topico = topicoOpt.get();
            // Si el tópico encontrado no es el mismo que estamos actualizando, es un duplicado
            if (!topico.getId().equals(datos.id())) {
                throw new ValidacionException("Ya existe otro tópico con ese título y mensaje.");
            }
        }
    }
}