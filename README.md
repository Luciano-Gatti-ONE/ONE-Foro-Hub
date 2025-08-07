
# 📣 ForoHub API

Bienvenido a **ForoHub API**, una RESTful API construida con Java y Spring Boot que permite la creación, gestión y visualización de foros temáticos. Este backend está diseñado para ser utilizado con clientes frontend y está preparado para producción, con seguridad JWT, validaciones, paginación y documentación integrada.

---

## 🚀 Tecnologías utilizadas

- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
- ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
- ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
- ![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
- ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
- ![Lombok](https://img.shields.io/badge/Lombok-A51C30?style=for-the-badge&logo=lombok&logoColor=white)

### Tecnologías para testing

- ![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)
- ![Mockito](https://img.shields.io/badge/Mockito-202020?style=for-the-badge&logo=mockito&logoColor=white)
- ![Spring Test](https://img.shields.io/badge/Spring_Test-6DB33F?style=for-the-badge&logo=spring&logoColor=white)

---

## 📁 Estructura del proyecto

```bash
src/
├── main/
│   └── java/com/forohub/api/
│       ├── domain/                 # Módulos de dominio organizados por entidad y clases de soporte
│       │   ├── curso/             # Entidad, repositorio, DTOs y servicios de Curso
│       │   ├── perfil/            # Entidad, repositorio, DTOs y servicios de Perfil
│       │   ├── topico/            # Entidad, repositorio, DTOs y servicios de Tópico
│       │   ├── respuesta/         # Entidad, repositorio, DTOs y servicios de Respuesta
│       │   ├── usuarios/          # Entidad, repositorio, DTOs y servicios de Usuario
│       │   └── ValidacionException.java  # Excepción personalizada para errores de validación del dominio
│       ├── controller/            # Controladores REST para exponer la API
│       │   ├── AutenticacionController.java
│       │   ├── CursoController.java
│       │   ├── RespuestaController.java
│       │   ├── TopicoController.java
│       │   └── UsuarioController.java
│       ├── infra/                 # Infraestructura y configuraciones
│       │   ├── errores/           # Manejo y definición de errores
│       │   ├── security/          # Configuración de seguridad (JWT, filtros)
│       │   └── springdoc/         # Configuraciones para documentación Swagger/OpenAPI
│       └── ApiApplication.java    # Clase principal para iniciar la aplicación
│  
└── test/                           # Pruebas unitarias y de integración
    ├── controller/                 # Tests para controladores REST (ej. TopicoControllerTest)
    └── domain/
        └── topico/                 # Tests para repositorio y lógica de dominio (ej. TopicoRepositoryTest)
```

---

## 🔐 Autenticación y Seguridad

- Login y registro de usuarios con validación.
- Generación y verificación de tokens JWT.
- Filtros personalizados para autorización.
- Protección de endpoints: solo los usuarios autenticados pueden acceder a recursos privados.

---

## 📄 Documentación

Accedé a la documentación completa de la API generada con Swagger en:

```
/swagger-ui/index.html
```

---

## ▶️ Endpoints destacados

| Método | Endpoint                   | Descripción                                          |
|--------|----------------------------|-----------------------------------------------------|
| POST   | /auth/login                | Autenticar usuario y obtener token JWT              |
| POST   | /auth/registrar            | Registrar nuevo usuario                              |
| GET    | /topicos                  | Listar tópicos con paginación y ordenamiento         |
| GET    | /topicos/por-fecha        | Listar tópicos ordenados por fecha                    |
| GET    | /topicos/por-curso-y-año  | Listar tópicos filtrados por curso y año (con filtro en body) |
| GET    | /topicos/{id}             | Obtener detalles de un tópico por su ID               |
| POST   | /topicos                  | Crear un nuevo tópico                                |
| PUT    | /topicos                  | Actualizar un tópico existente                       |
| DELETE | /topicos/{id}             | Eliminar un tópico                                   |
---

## ⚙️ Requisitos

- Java 17+
- Maven 3+
- MySQL o cualquier otra base de datos compatible con JPA

---

## 📦 Instalación y ejecución

```bash
# Clonar el repositorio
git clone https://github.com/tuusuario/forohub-api.git
cd forohub-api

# Configurar credenciales en application.properties

# Compilar y ejecutar
mvn spring-boot:run
```

---

## 🧑‍💻 Autor

Desarrollado por **Luciano Gatti Flekenstein**.


