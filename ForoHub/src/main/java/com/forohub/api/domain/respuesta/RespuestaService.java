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
 * Servicio que gestiona la lógica de negocio relacionada con las respuestas
 * en el foro.
 * 
 * Proporciona métodos para crear, listar, buscar, actualizar,
 * desactivar (borrado lógico) y marcar como solución las respuestas.
 * 
 * Utiliza repositorios para acceder a datos de respuestas, tópicos y usuarios.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@Service
public class RespuestaService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crea una nueva respuesta asociada a un usuario y un tópico.
     * 
     * @param datos DTO con la información necesaria para crear la respuesta
     * @return DTO con los detalles de la respuesta creada
     * @throws ValidacionException si el usuario no existe o está inactivo, o si el tópico no existe
     */
    public DatosDetalleRespuesta crearRespuesta(DatosCreacionRespuesta datos) {
        var autorRespuesta = usuarioRepository.findByActivoTrueAndId(datos.idAutor())
            .orElseThrow(() -> new ValidacionException("El usuario no existe o se encuentra inactivo"));

        var topico = topicoRepository.findById(datos.idTopico())
            .orElseThrow(() -> new ValidacionException("No existe un tópico con el id informado"));

        var respuesta = new Respuesta(datos.mensaje(), autorRespuesta, topico);
        respuestaRepository.save(respuesta);

        return new DatosDetalleRespuesta(respuesta);
    }

    /**
     * Lista todas las respuestas activas para un tópico dado.
     * 
     * @param id ID del tópico para filtrar las respuestas
     * @return lista de DTOs con la información de las respuestas activas
     */
    public List<DatosMostrarRespuesta> listarRespuestasPorTopico(Long id) {
        var respuestas = respuestaRepository.findByActivoTrueAndTopicoId(id)
            .stream()
            .map(DatosMostrarRespuesta::new)
            .collect(Collectors.toList());

        return respuestas;
    }

    /**
     * Obtiene una respuesta por su ID.
     * 
     * @param id ID de la respuesta a buscar
     * @return DTO con la información de la respuesta encontrada
     * @throws ValidacionException si la respuesta no existe
     */
    public DatosMostrarRespuesta respuestaPorId(Long id) {
        var respuestaEntity = respuestaRepository.findById(id)
            .orElseThrow(() -> new ValidacionException("No existe una respuesta con el id informado"));

        return new DatosMostrarRespuesta(respuestaEntity);
    }

    /**
     * Actualiza una respuesta existente con los datos proporcionados.
     * 
     * @param datos DTO con los datos para actualizar la respuesta
     * @return DTO con los detalles de la respuesta actualizada y avisos si hubo errores
     * @throws ValidacionException si la respuesta no existe
     */
    public DatosDetalleRespuestaActualizada actualizarRespuesta(DatosActualizarRespuesta datos) {
        var respuestaEntity = respuestaRepository.findById(datos.id())
            .orElseThrow(() -> new ValidacionException("Respuesta no encontrada"));

        var avisos = actualizarDatos(datos, respuestaEntity);
        respuestaRepository.save(respuestaEntity);

        return new DatosDetalleRespuestaActualizada(respuestaEntity, avisos);
    }

    /**
     * Realiza un borrado lógico de una respuesta, desactivándola.
     * 
     * @param id ID de la respuesta a desactivar
     */
    public void desactivarRespuesta(Long id) {
        var respuesta = respuestaRepository.getReferenceById(id);
        respuesta.desactivarRespuesta();
    }

    /**
     * Marca una respuesta como solución para el tópico correspondiente.
     * 
     * @param id ID de la respuesta a marcar como solución
     */
    public void marcarComoSolucion(Long id) {
        var respuesta = respuestaRepository.getReferenceById(id);
        respuesta.marcarSolucion();
    }

    /**
     * Actualiza los datos de la respuesta con los valores proporcionados en el DTO.
     * 
     * @param datosActualizarRespuesta DTO con los nuevos valores para la respuesta
     * @param respuesta entidad de respuesta a actualizar
     * @return mapa con avisos o mensajes de error detectados durante la actualización
     */
    public Map<String, String> actualizarDatos(DatosActualizarRespuesta datosActualizarRespuesta, Respuesta respuesta) {
        Map<String, String> avisos = new HashMap<>();

        if (datosActualizarRespuesta.mensaje() != null && !datosActualizarRespuesta.mensaje().isBlank()) {
            respuesta.setMensaje(datosActualizarRespuesta.mensaje());
        } else {
            avisos.put("Mensaje", "El mensaje se encuentra vacío");
        }

        return avisos;
    }
}