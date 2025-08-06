package com.forohub.api.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para actualizar los datos de un usuario.
 * Contiene el identificador obligatorio y los campos que pueden ser modificados:
 * nombre, correo electrónico y contraseña.
 * 
 * Validaciones aplicadas:
 * - El campo 'id' es obligatorio.
 * - El campo 'correoElectronico' debe tener formato válido si se proporciona.
 * 
 * @author usuario
 */

public record DatosActualizarUsuario(
        @NotNull(message = "El id es obligatorio")
        Long id,

        String nombre,

        @Email(message = "El email debe tener un formato válido")
        String correoElectronico,

        String contrasena
) {
}