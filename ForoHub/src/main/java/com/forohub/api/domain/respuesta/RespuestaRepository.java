package com.forohub.api.domain.respuesta;

import org.springframework.data.jpa.repository.JpaRepository;
import com.forohub.api.domain.respuesta.Respuesta;
import java.util.List;

/**
 *
 * @author usuario
 */
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByActivoTrueAndTopicoId(Long id);
}
