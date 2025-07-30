package com.forohub.api.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import com.forohub.api.domain.curso.Curso;

/**
 *
 * @author usuario
 */
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
