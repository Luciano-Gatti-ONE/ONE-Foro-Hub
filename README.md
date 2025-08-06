
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

---

## 📂 Estructura del proyecto

```bash
src/
├── main/
│   ├── java/com/forohub/api
│   │   ├── controller/       # Controladores REST
│   │   ├── dto/              # Clases DTO para requests/responses
│   │   ├── entity/           # Entidades JPA
│   │   ├── repository/       # Interfaces de acceso a datos
│   │   ├── security/         # Configuración de seguridad JWT
│   │   └── service/          # Lógica de negocio
│   └── resources/
│       ├── application.properties
│       └── static/
└── test/                     # Pruebas unitarias y de integración
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

| Método | Endpoint              | Descripción                     |
|--------|------------------------|----------------------------------|
| POST   | /auth/login           | Login y obtención de JWT         |
| POST   | /auth/registrar       | Registro de nuevos usuarios      |
| GET    | /topicos              | Listado de tópicos               |
| POST   | /topicos              | Crear un nuevo tópico            |
| PUT    | /topicos              | Actualizar un tópico existente   |
| DELETE | /topicos/{id}         | Eliminar un tópico               |
| GET    | /respuestas/{topico} | Listar respuestas por tópico     |

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
## Autores

- Luciano Emmanuel Gatti Flekenstein

