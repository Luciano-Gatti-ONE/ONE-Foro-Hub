package com.forohub.api.controller;

import com.forohub.api.domain.topico.DatosCreacionTopico;
import com.forohub.api.domain.topico.DatosRespuestaTopico;
import com.forohub.api.domain.topico.TopicoService;
import com.forohub.api.domain.usuarios.Usuario;
import com.forohub.api.domain.curso.Curso;
import com.forohub.api.domain.curso.Categoria;
import com.forohub.api.domain.topico.Topico;

import java.util.ArrayList;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.mockito.Mockito;

/**
 * Clase de pruebas para el controlador de tópicos.
 * <p>
 * Verifica el comportamiento del endpoint POST /topicos
 * en distintos escenarios: cuando la solicitud es inválida
 * y cuando es válida.
 * </p>
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Import(TopicoControllerTest.TestConfig.class) // Importa configuración de test con mocks
public class TopicoControllerTest {

    /**
     * MockMvc permite simular peticiones HTTP al controlador
     * sin iniciar un servidor real.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * JacksonTesters para serializar y deserializar
     * objetos JSON del DTO de creación y respuesta de tópicos.
     */
    @Autowired
    private JacksonTester<DatosCreacionTopico> datosCreacionTopicoJson;
    @Autowired
    private JacksonTester<DatosRespuestaTopico> datosRespuestaTopicoJson;

    /**
     * Servicio mockeado que simula la lógica de negocio
     * para los tópicos. Es inyectado por la configuración
     * de test declarada abajo.
     */
    @Autowired
    private TopicoService topicoService;

    /**
     * Configuración de test para proveer mocks de beans.
     * Define un bean TopicoService mockeado con Mockito,
     * que será usado en lugar del bean real en este contexto.
     */
    @TestConfiguration
    static class TestConfig {
        @Bean
        public TopicoService topicoService() {
            return Mockito.mock(TopicoService.class);
        }
    }

    /**
     * Test que verifica que al enviar una solicitud POST
     * sin cuerpo JSON, el controlador responde con un error
     * HTTP 400 Bad Request.
     */
    @Test
    @DisplayName("Deberia devolver http 400 cuando la request no tenga datos")
    @WithMockUser // Simula un usuario autenticado para pasar seguridad
    void crearTopico_escenarioInvalido() throws Exception {
        var response = mvc.perform(post("/topicos")) // POST sin cuerpo
                .andReturn().getResponse();

        // Verifica que el status HTTP sea 400
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test que verifica que al enviar una solicitud POST con
     * un JSON válido, el controlador responde con HTTP 201 Created
     * y un JSON con la información del tópico creado.
     */
    @Test
    @DisplayName("Deberia devolver http 201 cuando la request reciba un json valido")
    @WithMockUser
    void crearTopico_escenarioValido() throws Exception {
        // Datos de entrada simulados para crear un tópico
        var datosEntrada = new DatosCreacionTopico(
            1L, 1L, "Título de prueba", "Mensaje de prueba"
        );

        // Simulación de entidades relacionadas: autor y curso
        var autor = new Usuario("Luciano Gatti", "luciano@mail.com", "password123");
        var curso = new Curso(1L, "Spring Boot", Categoria.PROGRAMACION, new ArrayList<>());

        // Creación del tópico esperado con ID simulado
        var topico = new Topico("Título de prueba", "Mensaje de prueba", autor, curso);
        topico.setId(10L);

        // Definición del comportamiento mock: cuando se crea un tópico,
        // devolver el DTO con el tópico simulado
        when(topicoService.crearTopico(any())).thenReturn(new DatosRespuestaTopico(topico));

        // Ejecución de la petición POST con JSON serializado
        var response = mvc.perform(post("/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(datosCreacionTopicoJson.write(datosEntrada).getJson())
        ).andReturn().getResponse();

        // JSON esperado en la respuesta
        var jsonEsperado = datosRespuestaTopicoJson.write(new DatosRespuestaTopico(topico)).getJson();

        // Verificaciones: código 201 y cuerpo JSON esperado
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}