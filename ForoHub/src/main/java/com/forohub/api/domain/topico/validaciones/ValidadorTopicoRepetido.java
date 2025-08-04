package com.forohub.api.domain.topico.validaciones;

/**
 *
 * @author lucia
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
