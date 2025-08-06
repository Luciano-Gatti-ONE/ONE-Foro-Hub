package com.forohub.api.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // <-- Faltaba este import
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Topico.
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas
 * sobre los tópicos almacenados en la base de datos.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    /**
     * Busca un tópico que coincida exactamente con el título y mensaje proporcionados.
     * 
     * @param titulo título del tópico a buscar
     * @param mensaje mensaje del tópico a buscar
     * @return Optional con el tópico encontrado o vacío si no existe
     */
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);

    /**
     * Obtiene los 10 tópicos más antiguos ordenados por fecha de creación ascendente.
     * 
     * @return lista con hasta 10 tópicos ordenados de más antiguo a más reciente
     */
    List<Topico> findTop10ByOrderByFechaDeCreacionAsc();

    /**
     * Consulta personalizada que obtiene los tópicos filtrados por nombre del curso y año de creación.
     * 
     * @param curso nombre del curso para filtrar los tópicos
     * @param anio año de creación para filtrar los tópicos (extraído del campo fechaDeCreacion)
     * @return lista de tópicos que cumplen con el filtro
     */
    @Query("""
        SELECT t FROM Topico t
        WHERE t.curso.nombre = :curso
        AND FUNCTION('YEAR', t.fechaDeCreacion) = :fecha
    """)
    List<Topico> buscarTopicosPorCursoYAño(@Param("curso") String curso, @Param("fecha") Long anio);
}