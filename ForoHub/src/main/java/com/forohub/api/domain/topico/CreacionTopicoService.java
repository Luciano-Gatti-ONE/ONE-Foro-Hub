/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */

package com.forohub.api.domain.topico;

import org.springframework.stereotype.Service;

/**
 *
 * @author usuario
 */
@Service
public class CreacionTopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;
    
    /*
    @Autowired
    private List<ValidadorDeConsultas> validadores;
    */
    
    public DatosDetalleTopico crearTopico(DatosCreacionTopico datos){

        if(!usuarioRepository.existsById(datos.idAutor())){
            throw new ValidacionException("No existe un usuario con el id informado");
        }

        if(!cursoRepository.existsById(datos.idCurso())){
            throw new ValidacionException("No existe un curso con el id informado");
        }

        //validaciones
        //validadores.forEach(v -> v.validar(datos));

        var autor = usuarioRepository.findById(datos.idAutor()).get();
        var curso = cursoRepository.findById(datos.idCurso()).get();
        var topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
        topicoRepository.save(topico);
        return new DatosDetalleTopico(topico);
    }
}
