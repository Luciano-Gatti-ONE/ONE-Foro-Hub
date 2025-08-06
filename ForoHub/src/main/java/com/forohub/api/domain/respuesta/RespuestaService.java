package com.forohub.api.domain.respuesta;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.forohub.api.domain.topico.TopicoRepository;
import com.forohub.api.domain.respuesta.RespuestaRepository;
import com.forohub.api.domain.usuarios.UsuarioRepository;
import com.forohub.api.domain.ValidacionException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.forohub.api.domain.respuesta.DatosActualizarRespuesta;
import com.forohub.api.domain.respuesta.DatosCreacionRespuesta;
import com.forohub.api.domain.respuesta.DatosDetalleRespuesta;
import com.forohub.api.domain.respuesta.DatosDetalleRespuestaActualizada;
import com.forohub.api.domain.respuesta.DatosMostrarRespuesta;
import com.forohub.api.domain.respuesta.Respuesta;

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
        var respuesta = new Respuesta(datos.mensaje(), autorRespuesta, topico);
        respuestaRepository.save(respuesta);
        return new DatosDetalleRespuesta(respuesta);
    }
    
    public List<DatosMostrarRespuesta> listarRespuestasPorTopico(Long id){
        var respuestas = respuestaRepository.findByActivoTrueAndTopicoId(id)
                .stream()
                .map(DatosMostrarRespuesta::new)
                .collect(Collectors.toList());
        return respuestas;
    }
    
    public DatosMostrarRespuesta respuestaPorId(Long id){
        var respuestaEntity = respuestaRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe una respuesta con el id informado"));
        var datosRespuesta = new DatosMostrarRespuesta(respuestaEntity);
        return datosRespuesta;
    }
    
    public DatosDetalleRespuestaActualizada actualizarRespuesta(DatosActualizarRespuesta datos){
        var respuestaEntity = respuestaRepository.findById(datos.id())
            .orElseThrow(() -> new ValidacionException("Respuesta no encontrada"));
        var avisos = actualizarDatos(datos, respuestaEntity);
        respuestaRepository.save(respuestaEntity); 
        return new DatosDetalleRespuestaActualizada(respuestaEntity, avisos);
    }
    
    public void desactivarRespuesta(Long id){
        var respuesta = respuestaRepository.getReferenceById(id);      
        respuesta.desactivarRespuesta();    
    }
    
    public void marcarComoSolucion(Long id){
        var respuesta = respuestaRepository.getReferenceById(id); 
        respuesta.marcarSolucion();
    }
    
    public Map<String, String> actualizarDatos(DatosActualizarRespuesta datosActualizarRespuesta, Respuesta respuesta) {
        Map<String, String> avisos = new HashMap<>();

        if (datosActualizarRespuesta.mensaje() != null && !datosActualizarRespuesta.mensaje().isBlank()) {
            respuesta.setMensaje(datosActualizarRespuesta.mensaje());
        } else {
            avisos.put("Mensaje", "El mensaje se encuentra vacio");
        }
        
        return avisos;
    }
}
