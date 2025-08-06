package com.forohub.api.domain.respuesta;

import org.springframework.stereotype.Service;

/**
 *
 * @author usuario
 */

@Service
public class RespuestaService {
    
    @Autowired
    private TopicoRepository topicoRepository;
    
    @Autowired
    private RespuestaRepository respuestaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public DatosDetalleRespuesta crearRespuesta(DatosCreacionRespuesta datos){
        var autorRespuesta = usuarioRepository.findByActivoTrueAndId(datos.idAutor())
                 .orElseThrow(() -> new ValidacionException("El usuario no existe o se encuentra inactivo"));
        var topico = topicoRepository.findById(datos.idTopico())
                 .orElseThrow(() -> new ValidacionException("No existe un topico con el id informado"));
        var respuesta = new Respuesta(datos, autorRespuesta, topico);
        respuestaRepository.save(respuesta);
        return new DatosDetalleRespuesta(respuesta);
    }
    
    public List<DatosMostrarRespuestas> listarRespuestasPorTopico(Long id){
        var respuestas = respuestaRepository.findAllByTopicoId(id)
                .stream()
                .map(DatosMostrarRespuestas::new)
                .collect(Collectors.toList());
        return respuestas;
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
    
    public Map<String, String> actualizarDatos(DatosActualizarRespuesta datosActualizarRespuesta, Respuesta respuesta) {
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
        return avisos;
    }
     
    public void desactivarRespuesta(Long id){
        var respuesta = respuestaRepository.getReferenceById(id);      
        respuesta.desactivarRespuesta();    
    }
}
