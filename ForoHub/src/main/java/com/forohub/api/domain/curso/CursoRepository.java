package com.forohub.api.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import com.forohub.api.domain.curso.Curso;

/**
 * Repositorio JPA para la entidad Curso.
 * Proporciona operaciones CRUD y de consulta sobre cursos en la base de datos.
 * Extiende JpaRepository para heredar métodos estándar.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
