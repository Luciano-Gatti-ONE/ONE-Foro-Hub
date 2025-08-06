package com.forohub.api.domain.topico.validaciones;

import com.forohub.api.domain.topico.DatosCreacionTopico;
import com.forohub.api.domain.topico.DatosActualizarTopico;

/**
 * Interfaz para definir validadores de reglas de negocio relacionadas con los tópicos.
 * 
 * Implementa validaciones específicas para la creación y actualización de tópicos,
 * asegurando que los datos cumplan con las reglas establecidas antes de persistirlos.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public interface ValidadorDeTopicos {
    
    /**
     * Valida los datos necesarios para la creación de un nuevo tópico.
     * 
     * @param datos DTO con la información para crear un tópico
     * @throws ValidacionException si alguna validación falla
     */
    void validarCreacion(DatosCreacionTopico datos);
    
    /**
     * Valida los datos para la actualización de un tópico existente.
     * 
     * @param datos DTO con la información para actualizar un tópico
     * @throws ValidacionException si alguna validación falla
     */
    void validarActualizacion(DatosActualizarTopico datos);
}