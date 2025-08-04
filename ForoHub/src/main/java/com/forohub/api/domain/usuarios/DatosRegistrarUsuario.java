package com.forohub.api.domain.usuarios;

/**
 *
 * @author usuario
 */
public record DatosRegistrarUsuario(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,

    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    String email,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
        message = "La contraseña debe tener al menos una mayúscula, un número y un carácter especial"
    )
    String password      
) {

}
