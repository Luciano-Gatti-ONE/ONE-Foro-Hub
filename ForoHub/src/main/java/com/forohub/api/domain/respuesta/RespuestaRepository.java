package com.forohub.api.domain.respuesta;

import org.springframework.data.jpa.repository.JpaRepository;
import com.forohub.api.domain.respuesta.Respuesta;
import java.util.List;

/**
 * Repositorio JPA para la entidad Respuesta.
 * 
 * Proporciona métodos para operaciones CRUD estándar y
 * un método personalizado para obtener respuestas activas
 * asociadas a un tópico específico.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    /**
     * Obtiene todas las respuestas activas asociadas al tópico con el ID dado.
     *
     * @param id ID del tópico
     * @return Lista de respuestas activas para el tópico especificado
     */
    List<Respuesta> findByActivoTrueAndTopicoId(Long id);
}