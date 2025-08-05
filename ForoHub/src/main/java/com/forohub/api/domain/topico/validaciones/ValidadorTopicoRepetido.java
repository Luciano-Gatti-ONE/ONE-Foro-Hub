package com.forohub.api.domain.topico.validaciones;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.forohub.api.domain.topico.DatosCreacionTopico;
import com.forohub.api.domain.topico.DatosActualizarTopico;
import com.forohub.api.domain.topico.TopicoRepository;
import com.forohub.api.domain.ValidacionException;

/**
 *
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Component
public class ValidadorTopicoRepetido implements ValidadorDeTopicos{
    @Autowired
    private TopicoRepository repository;

    public void validarCreacion(DatosCreacionTopico datos){
        var topico = repository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if(topico.isPresent()){
            throw new ValidacionException("Ya existe un topico con ese titulo y mensaje");
        }
    }
    
    public void validarActualizacion(DatosActualizarTopico datos){
        var topico = repository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if(topico.isPresent()){
            throw new ValidacionException("Ya existe un topico con ese titulo y mensaje");
        }
    }
}
