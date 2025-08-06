package com.forohub.api.domain.usuarios;

/**
 * DTO que representa los datos necesarios para autenticar a un usuario.
 * Contiene el login (nombre de usuario o email) y la clave (contraseña).
 * 
 * Usado para recibir las credenciales en el proceso de autenticación.
 * 
 * @author Luciano Emmanuel Gatti Flekenstein
 */

public record DatosAutenticacionUsuario(
        String login, 
        String clave
) {}