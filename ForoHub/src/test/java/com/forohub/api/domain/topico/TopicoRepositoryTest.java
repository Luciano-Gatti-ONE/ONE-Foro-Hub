package com.forohub.api.domain.topico;

import jakarta.persistence.EntityManager;
import com.forohub.api.domain.curso.Curso;
import com.forohub.api.domain.curso.DatosCreacionCurso;
import com.forohub.api.domain.usuarios.Usuario;
import com.forohub.api.domain.topico.Topico;
import com.forohub.api.domain.topico.Status;
import com.forohub.api.domain.topico.TopicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Clase de prueba para el repositorio de tópicos.
 * <p>
 * Se encarga de verificar el comportamiento del método personalizado del repositorio:
 * {@code buscarTopicosPorCursoYAño}, bajo distintos escenarios.
 * </p>
 * 
 * <p><strong>Escenarios cubiertos:</strong></p>
 * <ul>
 *   <li>El tópico se encuentra correctamente cuando coinciden nombre del curso y año.</li>
 *   <li>No se encuentra el tópico cuando el año no coincide.</li>
 *   <li>No se encuentra el tópico cuando el nombre del curso no coincide.</li>
 * </ul>
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TopicoRepositoryTest {
    
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private EntityManager em;

    /**
     * Se ejecuta antes de cada test para dejar la base de datos en un estado limpio.
     * Elimina todos los registros previos y crea un autor, un curso y un tópico de prueba.
     */
    @BeforeEach
    void setUp() {
        topicoRepository.deleteAll();
        em.createQuery("DELETE FROM Usuario").executeUpdate();
        em.createQuery("DELETE FROM Curso").executeUpdate();

        var autor = registrarAutor("Juan Pérez", "juan@email.com", "123456");
        var curso = registrarCurso("Spring Boot", "Programacion");

        registrarTopico("Duda sobre JPA", "¿Cómo usar @Query en Spring?", autor, curso);
    }

    /**
     * Verifica que el repositorio devuelva correctamente un tópico
     * cuando el nombre del curso y el año coinciden con los datos almacenados.
     */
    @Test
    @DisplayName("Debe devolver tópico cuando el nombre del curso y año coinciden")
    void buscarTopicoPorCursoYAñoCoincidente() {
        List<Topico> resultado = topicoRepository.buscarTopicosPorCursoYAño("Spring Boot", 2025L);
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getTitulo()).isEqualTo("Duda sobre JPA");
    }

    /**
     * Verifica que el repositorio no devuelva ningún resultado
     * cuando el año proporcionado no coincide con el del tópico registrado.
     */
    @Test
    @DisplayName("No debe devolver tópicos cuando el año no coincide")
    void buscarTopicoPorAnioIncorrecto() {
        List<Topico> resultado = topicoRepository.buscarTopicosPorCursoYAño("Spring Boot", 2024L);
        assertThat(resultado).isEmpty();
    }

    /**
     * Verifica que el repositorio no devuelva ningún resultado
     * cuando el nombre del curso proporcionado no coincide con el del tópico registrado.
     */
    @Test
    @DisplayName("No debe devolver tópicos cuando el nombre del curso no coincide")
    void buscarTopicoPorCursoIncorrecto() {
        List<Topico> resultado = topicoRepository.buscarTopicosPorCursoYAño("Java Básico", 2025L);
        assertThat(resultado).isEmpty();
    }

    // Métodos auxiliares
    private void registrarTopico(String titulo, String mensaje, Usuario autor, Curso curso) {
        Topico topico = new Topico(titulo, mensaje, autor, curso);
        topico.setFechaDeCreacion(LocalDateTime.of(2025, 8, 7, 10, 0));
        em.persist(topico);
    }

    private Curso registrarCurso(String nombre, String categoria) {
        var curso = new Curso(datosCurso(nombre, categoria));
        em.persist(curso);
        return curso;
    }

    private Usuario registrarAutor(String nombre, String email, String documento) {
        var usuario = new Usuario(nombre, email, documento);
        em.persist(usuario);
        return usuario;
    }

    private DatosCreacionCurso datosCurso(String nombre, String categoria) {
        return new DatosCreacionCurso(nombre, categoria);
    }
}
