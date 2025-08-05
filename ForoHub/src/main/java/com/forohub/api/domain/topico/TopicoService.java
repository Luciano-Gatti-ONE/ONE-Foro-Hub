package com.forohub.api.domain.topico;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.forohub.api.domain.curso.CursoRepository;
import com.forohub.api.domain.usuarios.UsuarioRepository;
import com.forohub.api.domain.topico.TopicoRepository;
import com.forohub.api.domain.topico.DatosRespuestaTopico;
import com.forohub.api.domain.topico.DatosRespuestaTopicoActualizado;
import com.forohub.api.domain.topico.DatosActualizarTopico;
import com.forohub.api.domain.respuesta.Respuesta;
import com.forohub.api.domain.ValidacionException;
import com.forohub.api.domain.topico.validaciones.ValidadorDeTopicos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


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
    
    @Autowired
    private List<ValidadorDeTopicos> validaciones;
    
    public DatosRespuestaTopico crearTopico(DatosCreacionTopico datos){
        validaciones.forEach(v -> v.validarCreacion(datos));
        var autor = usuarioRepository.findById(datos.idAutor())
                 .orElseThrow(() -> new ValidacionException("No existe un usuario con el id informado"));
        var curso = cursoRepository.findById(datos.idCurso())
                 .orElseThrow(() -> new ValidacionException("No existe un curso con el id informado"));
        var topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
        topicoRepository.save(topico);
        
        return new DatosRespuestaTopico(topico);
    }
    
    public Page<DatosMostrarTopico> listarTodosLosTopicos(int page, int size, String sort){
        Pageable paginacion = PageRequest.of(page, size, Sort.by(sort));
        var topicos = topicoRepository.findAll(paginacion)
                .map(DatosMostrarTopico::new);
        return topicos;
    }
    
    public DatosMostrarTopico mostrarTopicoPorId(Long id){
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe un usuario con el id informado"));
        var respuesta = new DatosMostrarTopico(topico);
        return respuesta;
    }
    
    public DatosRespuestaTopicoActualizado actualizarTopico(DatosActualizarTopico datos){
        validaciones.forEach(v -> v.validarActualizacion(datos));
        var topico = topicoRepository.findById(datos.id())
            .orElseThrow(() -> new ValidacionException("Tópico no encontrado con id: " + datos.id()));
        var avisos = actualizarDatos(datos, topico);
        topicoRepository.save(topico); 
        return new DatosRespuestaTopicoActualizado(topico, avisos);
    }
    
    public void eliminarTopico(Long id){
        topicoRepository.deleteById(id);       
    }
    
    public List<DatosRespuestaTopico> topicosPorFecha(){
        var topicos = topicoRepository.findTop10ByOrderByFechaDeCreacionAsc();
        var respuesta = topicos.stream()
            .map(DatosRespuestaTopico::new)
            .toList();
        return respuesta;
    }
    

    public List<DatosRespuestaTopico> topicosPorCursoyAño(DatosBusquedaTopico datosBusquedaTopico){
        var topicos = topicoRepository.buscarTopicosPorCursoYAño(
                datosBusquedaTopico.nombreCurso(), 
                datosBusquedaTopico.añoTopico()
        );
        var respuesta = topicos.stream()
                .map(DatosRespuestaTopico::new)
                .collect(Collectors.toList());
        return respuesta;
    }
    
    public Map<String, String> actualizarDatos(DatosActualizarTopico datosActualizarTopico, Topico topico) {
        Map<String, String> avisos = new HashMap<>();

        if (datosActualizarTopico.titulo() != null && !datosActualizarTopico.titulo().isBlank()) {
            topico.setTitulo(datosActualizarTopico.titulo());
        } else if (datosActualizarTopico.titulo() != null) {
            avisos.put("Titulo", "El título no fue actualizado porque está vacío o en blanco.");
        }

        if (datosActualizarTopico.mensaje() != null && !datosActualizarTopico.mensaje().isBlank()) {
            topico.setMensaje(datosActualizarTopico.mensaje());
        } else if (datosActualizarTopico.mensaje() != null) {
            avisos.put("Mensaje", "El mensaje no fue actualizado porque está vacío o en blanco.");
        }

        if (datosActualizarTopico.status() != null) {
            List<Respuesta> respuestas = topico.getRespuestas();
            String status = datosActualizarTopico.status();
            switch (status) {
                case "SIN_RESPUESTAS":
                    if (respuestas == null || respuestas.isEmpty()) {
                        topico.setStatus(Status.SIN_RESPUESTAS);
                    } else {
                        avisos.put("Status", "No se pudo cambiar a SIN_RESPUESTAS: ya hay respuestas registradas.");
                    }
                    break;
                case "EN_CURSO":
                    if (respuestas != null && !respuestas.isEmpty()) {
                        topico.setStatus(Status.EN_CURSO);
                    } else {
                        avisos.put("Status", "No se pudo cambiar a EN_CURSO: no hay respuestas.");
                    }
                    break;
                case "RESUELTO":
                    if (respuestas != null && respuestas.stream().anyMatch(Respuesta::getSolucion)) {
                        topico.setStatus(Status.RESUELTO);
                    } else {
                        avisos.put("Status", "No se pudo cambiar a RESUELTO: ninguna respuesta fue marcada como solución.");
                    }
                    break;
                default:
                    avisos.put("Status", "Estado no válido: " + status);
            }
        }
        return avisos;
    }
    
    /**
    *
    * METODO UTILIZADO PARA LA ELIMINACION LOGICA DE UN TOPICO
    * 
    *   public void desactivarTopico(Long id){
    *        var topico = topicoRepository.getReferenceById(id);      
    *           topico.desactivarTopico();    
    *   }
    * 
    * METODO PARA LISTAR TODOS LOS TOPICOS SIN PAGEABLE
    *
    *    public List<DatosMostrarTopico> listarTodosLosTopicos(){
    *       var topicos = topicoRepository.findAll();
    *        var respuesta = topicos.stream()
    *                .map(DatosMostrarTopico::new)
    *                .toList();
    *        return respuesta;
    *    }
    */
}
