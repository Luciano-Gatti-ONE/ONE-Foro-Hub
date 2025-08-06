package com.forohub.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configura la documentación de OpenAPI para la aplicación.
 * 
 * Define el esquema de seguridad basado en JWT para proteger los endpoints
 * utilizando el esquema de autenticación tipo Bearer.
 */

@Configuration
public class SpringDocConfiguration {

    /**
     * Crea y configura una instancia de OpenAPI personalizada.
     * 
     * Este método define componentes adicionales para la documentación de la API,
     * específicamente un esquema de seguridad basado en JWT utilizando el esquema
     * HTTP tipo "bearer". Esto es útil para que Swagger UI pueda enviar el token
     * JWT en las peticiones a endpoints protegidos.
     *
     * @return una instancia configurada de OpenAPI
     */
     @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("bearer-key",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
            .info(new Info()
                .title("ForoHub API")
                .version("1.0.0")  // <-- Aquí ponés la versión real de tu API
                .description("API REST para gestión de tópicos, respuestas y usuarios")
                .contact(new Contact().name("Luciano Emmanuel Gatti Flekenstein").email("tu-email@ejemplo.com"))
            );
    }
}
