package com.forohub.api.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // <-- Faltaba este import
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);
    List<Topico> findTop10ByOrderByFechaDeCreacionAsc();

    @Query("""
        SELECT t FROM Topico t
        WHERE t.curso.nombre = :curso
        AND FUNCTION('YEAR', t.fechaDeCreacion) = :fecha
    """)
    List<Topico> buscarTopicosPorCursoYAño(@Param("curso") String curso, @Param("fecha") Long anio);
}