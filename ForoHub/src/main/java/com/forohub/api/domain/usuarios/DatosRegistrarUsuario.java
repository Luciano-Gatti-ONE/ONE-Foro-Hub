package com.forohub.api.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO para registrar un nuevo usuario en el sistema.
 * Contiene validaciones para asegurar que los datos obligatorios estén presentes
 * y que la contraseña cumpla con los requisitos de seguridad mínimos.
 * 
 * Validaciones:
 * - Nombre obligatorio (no puede estar vacío).
 * - Email obligatorio y con formato válido.
 * - Contraseña obligatoria, con mínimo 8 caracteres, al menos una mayúscula,
 *   un número y un carácter especial.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosRegistrarUsuario(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,

    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    String correoElectronico,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
        message = "La contraseña debe tener al menos una mayúscula, un número y un carácter especial"
    )
    String contrasena      
) {}