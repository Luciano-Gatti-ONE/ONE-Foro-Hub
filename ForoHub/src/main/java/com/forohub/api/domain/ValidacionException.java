package com.forohub.api.domain;

/**
 * Excepción personalizada utilizada para representar errores de validación
 * dentro del dominio de la aplicación.
 *
 * <p>
 * Esta excepción se lanza cuando alguna regla de negocio o validación de datos
 * no se cumple. Al extender de {@link RuntimeException}, no es obligatorio
 * capturarla explícitamente.
 * </p>
 *
 * <p>
 * Ejemplo de uso:
 * <pre>
 * if (usuario.getEdad() < 18) {
 *     throw new ValidacionException("El usuario debe ser mayor de edad");
 * }
 * </pre>
 * </p>
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 * @version 1.0
 */

public class ValidacionException extends RuntimeException {
    
    /**
     * Crea una nueva instancia de ValidacionException con un mensaje específico.
     *
     * @param mensaje el detalle del error de validación
     */
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}
