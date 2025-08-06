package com.forohub.api.infra.security;

/** 
 * Esta clase representa una estructura de datos inmutable (record) para encapsular el token JWT.
 * Un 'record' en Java crea automáticamente un constructor, getters y métodos como equals(), hashCode() y toString().
 * Se utiliza para retornar el token generado al cliente después de la autenticación.
 */

public record DatosJWTToken(
        String jwTtoken
) {}