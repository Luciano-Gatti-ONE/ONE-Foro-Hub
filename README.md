# ForoHub API

API RESTful para la gestión de un foro educativo donde los usuarios pueden crear cursos, tópicos y respuestas.

## Tabla de Contenidos

- [Descripción](#descripción)
- [Tecnologías](#tecnologías)
- [Características](#características)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Autores](#autores)

## Descripción

ForoHub API es un backend desarrollado en Java con Spring Boot que permite gestionar usuarios, cursos, tópicos (preguntas) y respuestas dentro de un foro educativo. Implementa autenticación con JWT y controla los accesos mediante roles.

## Tecnologías

- Java 17
- Spring Boot
- Spring Security con JWT
- Hibernate / JPA
- MySQL (o cualquier base de datos relacional compatible)
- Maven
- Lombok

## Características

- Registro, autenticación y actualización de usuarios con seguridad.
- Gestión de cursos con categorías.
- Creación y consulta de tópicos con estados (sin respuesta, en curso, resuelto).
- Respuestas a tópicos con opción para marcar solución.
- Paginación y búsqueda avanzada.
- Validaciones personalizadas y manejo centralizado de errores.
- Documentación Swagger (OpenAPI).

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu_usuario/forohub-api.git
   cd forohub-api
   ```

2. Configurar la base de datos en `application.properties` o `application.yml`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/forohub
   spring.datasource.username=usuario
   spring.datasource.password=contraseña
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Configurar la clave secreta para JWT en el archivo de propiedades:
   ```properties
   api.security.secret=tu_clave_secreta
   ```

4. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

## Uso

- La API expone endpoints para manejar usuarios, cursos, tópicos y respuestas.
- La autenticación se realiza mediante JWT en el header `Authorization: Bearer <token>`.
- Endpoints públicos:
  - `POST /auth/login` para login.
  - `POST /auth/registrar` para registrar usuario nuevo.
- Endpoints protegidos requieren token válido.

## Autores

- Luciano Emmanuel Gatti Flekenstein

