# EmpleosApp


> Web application for managing vacancies, categories, users, and applications
with CV uploading, developed with Spring Boot and Thymeleaf.

This README is also available in [Spanish ES](./README.es.md)

**EmpleosApp** is a backend + web application for managing vacancies, categories, users, and applications on a job portal.
It offers role-based access, form validation, and administrative features to efficiently manage content through a web interface.

---

## Technologies Used

| Tool              | Purpose                            |
|-------------------|------------------------------------|
| Java 17           | Main programming language          |
| Spring Boot 3.3.2 | Backend framework                  |
| Spring Web        | REST/web endpoints                 |
| Thymeleaf         | Server-side HTML templating engine |
| Spring Data JPA   | ORM and persistence layer          |
| Spring Security   | Authentication & role management   |
| Spring Validation | Backend input validations          |
| MySQL 8           | Relational database                |
| Docker Compose    | Container for MySQL                |
| JUnit 5           | Unit & integration testing         |

---

##  Features

- **Authentication & Authorization** with Spring Security
-  **Job category management** (CRUD with pagination)
-  **Vacancy management**
-  **Posting and managing** applications with CV file uploads
-  **User management** with listing and deletion
-  **Advanced search**with pagination of vacancies
-  **Password encryption with BCrypt**
-  Session management and secure authentication

---

##  Validations & Rules
### Examples

-  **Role-based access** for protected routes
-  **Job Applications** Only authenticated users can submit applications with CVs
-  **Active Vacancies** Only vacancies with active status are displayed to the general public
-  **Advanced Search** allows you to filter vacancies by description, category, and status
-  **File Uploads** CV upload validation (format, size, name)
-  **BCrypt password encryption** (if enabled)

---

## Project Structure

- controllers/     — Web controllers (handle views and routes)
- config/          — General configuration (security, application settings, etc.)
- models/          — Entities and JPA
- repositories/    — repositories
- security/        — security config
- service/         — Service interfaces and business logic implementations
- resources/       — Configuration files (application.properties, etc.)
- templates/       — Thymeleaf HTML templates
- static/          — Static assets (CSS, JS, images)
- util/            — Utility classes (roles, validators, helpers)
- test/            — Unit and integration tests

### Architecture
The project is designed with a layered MVC architecture, allowing for a
clear separation of responsibilities between the presentation layer,
business logic, and data persistence. It also ensures modularity and
scalability by following common Spring Boot practices.

---
##  Local Installation

### 1. Prerequisites

- Java 17 installed
- Docker and Docker Compose
- Maven

### 2. Clone the repository
git clone https://github.com/alejandrorivera22/empleosapp.git
cd empleosapp

### 3. Custom Configuration (Image Path and Cvs)
- On Windows:
  **empleos.ruta.images=C:\\Users\\YourUser\\empleosapp\\images\\**
  **empleos.ruta.cv=C:\\Users\\YourUser\\empleosapp\\filescv\\**
- On Linux/macOS:
  **empleos.ruta.images=/home/youruser/empleosapp/images/**
  **empleos.ruta.cv=/home/youruser/empleosapp/filescv/**

### 4. Sample Images
- **Important:** You must copy these images from src/main/resources/copy/images to the folder defined in empleos.ruta.images and these CVs from src/main/resources/copy/files-cv to the folder defined in empleos.ruta.cv
so that the application can display them correctly at runtime.

### 5. Start MySQL
- docker-compose up -d

### 6. Build and run the application
- ./mvnw clean install
- ./mvnw spring-boot:run

### 7. Access to the web:
- http://localhost:8080

---

## Predefined Test Users

These users are preloaded into the database (`data.sql`)  
and can be used to simulate authentication and authorization  
according to the different roles available in the system.

| Rol      | Username       | Contraseña |
|----------|----------------|------------|
| EDITOR    | `luis`         | `luis123`  |
| GERENTE   | `marisol `     | `mari123`  |

> Passwords are encrypted using BCrypt.  
> These credentials are provided for local testing purposes only.

---
**Alejandro Rivera**
- [![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?logo=linkedin)](https://www.linkedin.com/in/alejandro-rivera-verdayes-dev/)
- [![GitHub](https://img.shields.io/badge/GitHub-000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/alejandrorivera22)
