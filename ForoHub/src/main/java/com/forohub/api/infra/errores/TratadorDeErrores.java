package com.forohub.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Clase global para el manejo centralizado de excepciones en la aplicación.
 * Utiliza @RestControllerAdvice para interceptar excepciones lanzadas
 * por los controladores REST y devolver respuestas HTTP adecuadas.
 * 
 * Registra los errores y mensajes de validación usando un logger.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

@RestControllerAdvice
public class TratadorDeErrores {

    // Logger para registrar información, advertencias y errores de la aplicación
    private static final Logger logger = LoggerFactory.getLogger(TratadorDeErrores.class);

    /**
     * Maneja excepciones cuando una entidad no es encontrada (por ejemplo, búsqueda por ID que no existe).
     * Responde con un código HTTP 404 (Not Found).
     *
     * @return ResponseEntity vacío con estado 404
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        logger.warn("Recurso no encontrado - EntityNotFoundException");
        return ResponseEntity.notFound().build();
    }

    /**
     * Maneja errores de validación cuando los datos de entrada no cumplen con las restricciones @Valid.
     * Recopila los errores de campo, los transforma en un formato legible y responde con código HTTP 400 (Bad Request).
     *
     * @param e excepción que contiene detalles de los errores de validación
     * @return ResponseEntity con lista de errores de validación y estado 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
        List<DatosErrorValidacion> errores = e.getFieldErrors().stream()
                .map(DatosErrorValidacion::new)
                .toList();

        logger.warn("Error de validación en el request: {}", errores);
        return ResponseEntity.badRequest().body(errores);
    }

    /**
     * Maneja excepciones personalizadas lanzadas para validaciones de negocio específicas.
     * Responde con código HTTP 400 y el mensaje personalizado de error.
     *
     * @param e excepción de validación de negocio con mensaje descriptivo
     * @return ResponseEntity con mensaje de error y estado 400
     */
    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity tratarErrorDeValidacion(ValidacionException e) {
        logger.warn("Validación de negocio fallida: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Clase interna que representa el detalle de un error de validación en un campo específico.
     * Contiene el nombre del campo y el mensaje de error asociado.
     */
    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}