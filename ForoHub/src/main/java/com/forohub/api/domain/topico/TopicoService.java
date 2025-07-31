package com.forohub.api.domain.topico;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.forohub.api.domain.curso.CursoRepository;
import com.forohub.api.domain.usuarios.UsuarioRepository;
import com.forohub.api.domain.topico.TopicoRepository;
import com.forohub.api.domain.topico.DatosRespuestaTopico;
import com.forohub.api.domain.ValidacionException;

/**
 *
 * @author usuario
 */
@Service
public class TopicoService {
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
    
    public DatosRespuestaTopico crearTopico(DatosCreacionTopico datos){
        //validaciones
        //validadores.forEach(v -> v.validar(datos));

        var autor = usuarioRepository.findById(datos.idAutor())
                 .orElseThrow(() -> new ValidacionException("No existe un usuario con el id informado"));
        var curso = cursoRepository.findById(datos.idCurso())
                 .orElseThrow(() -> new ValidacionException("No existe un curso con el id informado"));
        var topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
        topicoRepository.save(topico);
        
        return new DatosRespuestaTopico(topico);
    }
    
    public DatosRespuestaTopico actualizarTopico(DatosActualizacionTopico datos){
        //validaciones
        //validadores.forEach(v -> v.validar(datos));

        var autor = usuarioRepository.findById(datos.idAutor())
                .orElseThrow(() -> new ValidacionException("No existe un usuario con el id informado"));;
        var curso = cursoRepository.findById(datos.idCurso())
                .orElseThrow(() -> new ValidacionException("No existe un curso con el id informado"));
        var topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
        topicoRepository.save(topico);
        return new DatosRespuestaTopico(topico);
    }
    
    public DatosRespuestaTopico mostrarTopicoPorId(){
        if(op == 1){
            var topicos = topicoRepository.findAll();
            var respuesta = topicos.stream()
                    .map(DatosRespuestaTopico::new)
                    .toList();
            
        }else{
            
        }
        return new DatosRespuestaTopico(topico);
    }
    
    public DatosRespuestaTopico topicosPorFecha(){
        if(op == 1){
            var topicos = topicoRepository.findAll();
            var respuesta = topicos.stream()
                    .map(DatosRespuestaTopico::new)
                    .toList();
            
        }else{
            
        }
        return new DatosRespuestaTopico(topico);
    }
    
    public DatosRespuestaTopico topicosPorCursoyAño(){
        if(op == 1){
            var topicos = topicoRepository.findAll();
            var respuesta = topicos.stream()
                    .map(DatosRespuestaTopico::new)
                    .toList();
            
        }else{
            
        }
        return new DatosRespuestaTopico(topico);
    }
    
    public DatosRespuestaTopico listarTodosLosTopicos(int id:0){
        if(op == 1){
            var topicos = topicoRepository.findAll();
            var respuesta = topicos.stream()
                    .map(DatosRespuestaTopico::new)
                    .toList();
            
        }else{
            
        }
        return new DatosRespuestaTopico(topico);
    }
}
