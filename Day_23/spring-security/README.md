# Day 23	Spring Security Basics Task

A simple Spring Boot project demonstrating user registration and authentication using Spring Security, JPA, and PostgreSQL.

## Features

- User registration with password hashing
- Authentication using HTTP Basic
- PostgreSQL database integration
- Secure endpoints with Spring Security

## Technologies

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven

## Setup

1. **Clone the repository**
2. **Configure database**  
   Update `src/main/resources/application.properties` with your PostgreSQL credentials.
3. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints

- `POST /auth/register` — Register a new user
- `GET /auth/hello` — Authenticated greeting

## Example Usage

```bash
# Register a new user
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"tanmay","password":"1234"}'
# Response:
User tanmay registered!

# Authenticate and access protected endpoint
curl -u tanmay:1234 http://localhost:8080/auth/hello
# Response:
Hello, authenticated user!
```

## Project Structure

- `model/User.java` — User entity
- `repository/UserRepository.java` — Data access
- `service/CustomUserDetailsService.java` — User details for authentication
- `controller/AuthController.java` — REST endpoints
- `config/SecurityConfig.java` — Security rules
- `SpringSecurityApplication.java` — Main application
