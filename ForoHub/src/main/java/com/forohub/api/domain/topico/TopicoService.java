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
 * Servicio que gestiona la lógica de negocio relacionada con los tópicos.
 * Proporciona métodos para crear, listar, actualizar, eliminar y filtrar tópicos.
 * Además, utiliza validadores para controlar la integridad y las reglas del dominio.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
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

    /**
     * Crea un nuevo tópico en el sistema.
     * Valida los datos mediante los validadores configurados antes de persistir.
     * 
     * @param datos DTO con la información para crear un tópico
     * @return DTO con los datos del tópico creado
     * @throws ValidacionException si no existe el autor o curso, o si alguna validación falla
     */
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

    /**
     * Lista todos los tópicos con paginación y ordenamiento.
     * 
     * @param page número de página (base 0)
     * @param size cantidad de elementos por página
     * @param sort campo por el cual ordenar los resultados
     * @return página con los datos de los tópicos a mostrar
     */
    public Page<DatosMostrarTopico> listarTodosLosTopicos(int page, int size, String sort){
        Pageable paginacion = PageRequest.of(page, size, Sort.by(sort));
        var topicos = topicoRepository.findAll(paginacion)
                .map(DatosMostrarTopico::new);
        return topicos;
    }

    /**
     * Obtiene los datos de un tópico específico por su ID.
     * 
     * @param id identificador del tópico
     * @return DTO con los datos para mostrar del tópico
     * @throws ValidacionException si no se encuentra el tópico
     */
    public DatosMostrarTopico mostrarTopicoPorId(Long id){
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe un tópico con el id informado"));
        return new DatosMostrarTopico(topico);
    }

    /**
     * Actualiza un tópico existente con los datos enviados.
     * Se validan los datos antes de la actualización.
     * 
     * @param datos DTO con la información para actualizar el tópico
     * @return DTO con los datos actualizados y avisos sobre validaciones
     * @throws ValidacionException si no se encuentra el tópico
     */
    public DatosRespuestaTopicoActualizado actualizarTopico(DatosActualizarTopico datos){
        validaciones.forEach(v -> v.validarActualizacion(datos));
        var topico = topicoRepository.findById(datos.id())
            .orElseThrow(() -> new ValidacionException("Tópico no encontrado con id: " + datos.id()));
        var avisos = actualizarDatos(datos, topico);
        topicoRepository.save(topico); 
        return new DatosRespuestaTopicoActualizado(topico, avisos);
    }

    /**
     * Elimina un tópico por su ID.
     * Actualmente realiza eliminación física, puede adaptarse para borrado lógico.
     * 
     * @param id identificador del tópico a eliminar
     */
    public void eliminarTopico(Long id){
        topicoRepository.deleteById(id);       
    }

    /**
     * Obtiene los 10 tópicos más antiguos ordenados ascendentemente por fecha de creación.
     * 
     * @return lista con los datos de los tópicos ordenados por fecha
     */
    public List<DatosRespuestaTopico> topicosPorFecha(){
        var topicos = topicoRepository.findTop10ByOrderByFechaDeCreacionAsc();
        return topicos.stream()
            .map(DatosRespuestaTopico::new)
            .toList();
    }

    /**
     * Obtiene los tópicos filtrados por curso y año, según los datos de búsqueda recibidos.
     * 
     * @param datosBusquedaTopico DTO con filtros de curso y año
     * @return lista con los datos de los tópicos filtrados
     */
    public List<DatosRespuestaTopico> topicosPorCursoyAño(DatosBusquedaTopico datosBusquedaTopico){
        var topicos = topicoRepository.buscarTopicosPorCursoYAño(
                datosBusquedaTopico.nombreCurso(), 
                datosBusquedaTopico.añoTopico()
        );
        return topicos.stream()
                .map(DatosRespuestaTopico::new)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza los datos del tópico con la información recibida.
     * Valida que los campos no estén vacíos y realiza validaciones específicas para el status.
     * 
     * @param datosActualizarTopico DTO con datos para actualizar
     * @param topico entidad a actualizar
     * @return mapa con avisos en caso de que algún dato no haya sido actualizado
     */
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
    
    /*
     * Métodos comentados para borrado lógico y listado sin paginación.
     * 
     * public void desactivarTopico(Long id){
     *    var topico = topicoRepository.getReferenceById(id);      
     *    topico.desactivarTopico();    
     * }
     * 
     * public List<DatosMostrarTopico> listarTodosLosTopicos(){
     *    var topicos = topicoRepository.findAll();
     *    return topicos.stream()
     *            .map(DatosMostrarTopico::new)
     *            .toList();
     * }
     */
}