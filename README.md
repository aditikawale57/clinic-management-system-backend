# Vaishnavi's Healthcare — Backend

A Spring Boot REST API for managing clinic patients, consultations, treatments, and users.

## Tech Stack

- Java 25
- Spring Boot 4.1 (Web MVC, Data JPA, Validation, Security)
- PostgreSQL 16
- SpringDoc OpenAPI (Swagger UI)
- Lombok
- Docker / Docker Compose

## Prerequisites

- Docker and Docker Compose

The app is built and run entirely inside Docker, so you do not need Java or Maven installed on your host.

## Running the Application

From the project root:

```bash
# Build and start the app + PostgreSQL
docker compose up -d --build

# Follow application logs
docker compose logs -f app

# Stop the containers (database data is preserved)
docker compose down

# Stop and wipe the database volume
docker compose down -v
```

Once started:

| Service    | URL / connection |
| ---------- | ---------------- |
| API        | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI    | http://localhost:8080/v3/api-docs |
| PostgreSQL | `localhost:5432` — database `clinic_db`, user `clinic`, password `root123` |

Hibernate auto-creates tables on startup (`spring.jpa.hibernate.ddl-auto=update`).

## API Overview

All feature endpoints are versioned under `/api/v1`. Controllers under `modules/` declare resource-relative paths (e.g. `/patients`); `WebConfig` applies the `/api/v1` prefix automatically.

### Health

| Method | Endpoint           | Auth     | Description        |
| ------ | ------------------ | -------- | ------------------ |
| GET    | `/api/v1/health`   | Public   | Liveness check     |

### Patients

| Method | Endpoint              | Auth   | Description        |
| ------ | --------------------- | ------ | ------------------ |
| POST   | `/api/v1/patients`    | Public | Register a patient |
| GET    | `/api/v1/patients`    | Public | List all patients  |

### Users

| Method | Endpoint                 | Auth        | Description     |
| ------ | ------------------------ | ----------- | --------------- |
| POST   | `/api/v1/users`          | JWT required | Create a user   |
| GET    | `/api/v1/users`          | JWT required | List all users  |
| GET    | `/api/v1/users/{id}`     | JWT required | Get user by ID  |
| PUT    | `/api/v1/users/{id}`     | JWT required | Update a user   |
| DELETE | `/api/v1/users/{id}`     | JWT required | Delete a user   |

User creation returns the standard `ApiResponse` envelope (`statusCode`, `message`, `data`). Patient endpoints return DTOs directly.

### Examples

Health check:

```bash
curl http://localhost:8080/api/v1/health
```

Create a patient:

```bash
curl -X POST http://localhost:8080/api/v1/patients \
  -H "Content-Type: application/json" \
  -d '{"name":"Asha","age":34,"gender":"Female"}'
```

List all patients:

```bash
curl http://localhost:8080/api/v1/patients
```

Create a user (requires a valid JWT bearer token):

```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "username": "dr.asha",
    "email": "asha@clinic.example",
    "password": "securepass",
    "roles": ["ROLE_DOCTOR"]
  }'
```

Available roles: `ROLE_ADMIN`, `ROLE_DOCTOR`, `ROLE_PATIENT`.

## Security

The API uses stateless JWT authentication via Spring Security. Public paths (no token required):

- `/api/v1/health/**`
- `/api/v1/auth/**` (reserved for login/refresh endpoints)
- `/api/v1/patients/**`
- `/swagger-ui/**`, `/swagger-ui.html`, `/v3/api-docs/**`

All other requests require a valid `Authorization: Bearer <token>` header.

## Configuration

Default settings live in `src/main/resources/application.properties`. When running via Docker Compose, database connection details are overridden through environment variables in `docker-compose.yml`.

JWT and environment settings are bound under the `app` prefix and can be overridden with environment variables (e.g. `APP_JWT_SECRET`, `APP_ENV`).

| Property                  | Default                                      |
| ------------------------- | -------------------------------------------- |
| `app.env`                 | `development`                                |
| `app.jwt.secret`          | `change-this-secret-in-production-minimum-32-chars` |
| `app.jwt.issuer`          | `vaishnavis-healthcare`                      |
| `app.jwt.audience`        | `vaishnavis-healthcare-client`               |
| `app.jwt.validity-seconds`| `3600`                                       |

## Local Development (optional)

If you have Java 25 and want to run outside Docker:

```bash
# Start only PostgreSQL
docker compose up -d postgres

# Run the app with the Maven wrapper
./mvnw spring-boot:run

# Run tests
./mvnw test
```

## Project Structure

```
src/main/java/com/vaishnavishealthcare/
├── VaishnavisHealthcareApplication.java   Application entry point
├── common/
│   ├── constant/       Shared constants (e.g. ApiPaths)
│   ├── controller/     Cross-cutting controllers (health)
│   ├── dto/            Standard ApiResponse envelope
│   ├── exception/      Custom exceptions + global handler
│   └── util/           Shared helpers
├── config/
│   ├── AppProperties.java                 Typed app configuration
│   ├── OpenApiConfig.java                 Swagger / OpenAPI setup
│   ├── WebConfig.java                     /api/v1 path prefix for modules
│   └── security/                          JWT filter, SecurityConfig
└── modules/
    ├── auth/
    │   ├── controller/   User REST API
    │   ├── service/      User business logic (+ impl/)
    │   ├── repository/   Spring Data JPA repositories
    │   ├── entity/       User, Role, RefreshToken
    │   └── dto/          Request/response DTOs
    ├── patient/
    │   ├── controller/   Patient REST API
    │   ├── service/      Patient business logic (+ impl/)
    │   ├── repository/   Spring Data JPA repositories
    │   ├── entity/       JPA entity (Patient)
    │   └── dto/          Request/response DTOs
    ├── consultation/
    │   ├── entity/       JPA entity (Consultation)
    │   └── repository/   Spring Data JPA repository
    └── treatment/
        ├── entity/       JPA entity (Treatment)
        └── repository/   Spring Data JPA repository
```
