package com.forohub.api.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
/**
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
