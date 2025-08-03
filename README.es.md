# EmpleosApp

> Aplicación web gestion de vacantes, categorías, usuarios y solicitudes
con subida de CV desarrollado con Spring Boot y Thymeleaf.

Este README se encuentra disponible [Inges EN](./README.md)

**EmpleosApp** es una aplicación web + backend para getionar vacantes,
categorias, usuarios y solicitudes en un portal de empleos.
Ofrece acceso basado en roles, validación de formularios y funcionalidades
administrativas para gestionar el contenido de manera eficiente a través
de una interfaz web.

---

## Technologies Used

| Tool              | Purpose                              |
|-------------------|--------------------------------------|
| Java 17           | 	Lenguaje principal                  |
| Spring Boot 3.3.2 | Framework backend                    |
| Spring Web        | REST/web endpoints                   |
| Thymeleaf         | Motor de plantillas HTML server-side |
| Spring Data JPA   | ORM y capa de persistencia           |
| Spring Security   | Autenticación y gestión de roles     |
| Spring Validation | Validaciones de entrada              |
| MySQL 8           | Base de datos relacional             |
| Docker Compose    | Contenedor para MySQL                |
| JUnit 5           | Pruebas unitarias y de integración   |

---

##  Funcionalidades

- **Autenticación y autorización** con Spring Security
-  **Gestión de categorías de empleos** (CRUD con paginación)
-  **Gestión de vacantes** 
-   **Publicación y administración** de solicitudes con subida de archivos CV
-  **Gestión de usuarios** con listado y eliminación
-  **Búsqueda avanzada** paginación de vacantes
-  **Encriptación de contraseñas con BCrypt**
-  Manejo de sesiones y autenticación segura

---

##  Validaciones y Reglas
### Ejemplos

-  **Acceso restringido** rutas protegidas
-  **Solicitudes de empleo** solo los usuarios autenticados pueden enviar solicitudes con CV
-  **Vacantes activas** solo se muestran vacantes con estatus activo al público general
-  **Búsqueda avanzada**  permite filtrar vacantes por descripción, categoría y estatus
-  **Carga de archivos**  validación de carga de CV (formato, tamaño, nombre)
-  **Contraseñas cifradas** con BCrypt (si está habilitado)

---

## Estructura del proyecto

- controllers/     — Web controllers (handle views and routes)
- config/          — General configuration (application settings, etc.)
- models/          — Entities and JPA
- repositories/    — repositories
- security/        — security config
- service/         — Service interfaces and business logic implementations
- resources/       — Configuration files (application.properties, etc.)
- templates/       — Thymeleaf HTML templates
- static/          — Static assets (CSS, JS, images)
- util/            — Utility classes (roles, validators, helpers)
- test/            — Unit and integration tests

### Arquitectura
El proyecto sigue una arquitectura en capas basada en el patrón MVC.
Permite una separación clara entre la presentación, la lógica de negocio y la persistencia de datos.
Esta estructura mejora la mantenibilidad, modularidad y escalabilidad.

---
##  Instalación Local

### 1. Requisitos Previos

- Java 17
- Docker y Docker Compose
- Maven

### 2. Clonar el repositorio
git clone https://github.com/alejandrorivera22/empleosapp.git
cd empleosapp

### 3. Configuración personalizada (Ruta de imágenes y CV)
- En Windows:
  **empleos.ruta.images=C:\\Users\\YourUser\\empleosapp\\images\\**
  **empleos.ruta.cv=C:\\Users\\YourUser\\empleosapp\\filescv\\**
- En Linux/macOS:
  **empleos.ruta.images=/home/youruser/empleosapp/images/**
  **empleos.ruta.cv=/home/youruser/empleosapp/filescv/**

### 4. Copiar imágenes de ejemplo
- **IMPORTANTE:** Debes copiar estas imágenes src/main/resources/copy/images a la carpeta definida enempleos.ruta.images y los estos CVs src/main/resources/copy/files-cv a la carpeta definida en empleos.ruta.cv
para que la aplicación pueda mostrarlas correctamente en el tiempo de ejecución.

### 5. Levantar MySQL
docker-compose up -d

### 6. Compilar y ejecutar la aplicación
- ./mvnw clean install
- ./mvnw spring-boot:run

### 7. Acceder a la aplicación web
- http://localhost:8080

---
## Usuarios de prueba

Estos usuarios están precargados en la base de datos (`data.sql`) 
y pueden utilizarse para simular la autenticación y la autorización según los diferentes roles disponibles en el sistema.

| Rol            | Username       | Contraseña |
|----------------|----------------|------------|
| SUPERVISOR     | `luis`         | `luis123`  |
| ADMINISTRADOR  | `marisol `     | `mari123`  |

>Las contraseñas se cifran con BCrypt.
> Estas credenciales se proporcionan únicamente para pruebas locales.

Autor
---
**Alejandro Rivera**
- [![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?logo=linkedin)](https://www.linkedin.com/in/alejandro-rivera-verdayes-dev/)
- [![GitHub](https://img.shields.io/badge/GitHub-000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/alejandrorivera22)
