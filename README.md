# Clinic Management System — Backend

A Spring Boot REST API for managing clinic patients, consultations, and treatments.

## Tech Stack

- Java 25
- Spring Boot 4.1 (Web MVC, Data JPA, Validation)
- PostgreSQL 16
- Lombok
- Docker / Docker Compose

## Prerequisites

- Docker and Docker Compose

That's it — the app is built and run entirely inside Docker, so you don't need Java or Maven installed on your host.

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

- API: http://localhost:8080
- PostgreSQL: localhost:5432 (database `clinic_db`, user `clinic`, password `root123`)

Hibernate auto-creates the tables on startup (`spring.jpa.hibernate.ddl-auto=update`).

## API Endpoints

Base path: `/api/patients`

| Method | Endpoint         | Description           |
| ------ | ---------------- | --------------------- |
| POST   | `/api/patients`  | Register a patient    |
| GET    | `/api/patients`  | List all patients     |

### Examples

Create a patient:

```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{"name":"Asha","age":34,"gender":"Female"}'
```

List all patients:

```bash
curl http://localhost:8080/api/patients
```

## Configuration

Default datasource settings live in `src/main/resources/application.properties`. When running via Docker Compose, the database host/credentials are overridden through environment variables in `docker-compose.yml`.

## Project Structure

The codebase is organized by feature, with each feature owning its own controller, service, repository, entity, and DTO layers:

```
src/main/java/com/vaishnavishealthcare/
├── VaishnavisHealthcareApplication.java   Application entry point
└── feature/
    ├── patient/
    │   ├── controller/   REST controllers
    │   ├── service/      Business logic (+ impl/)
    │   ├── repository/   Spring Data JPA repositories
    │   ├── entity/       JPA entity (Patient)
    │   └── dto/          Request/response DTOs
    ├── consultation/
    │   ├── entity/       JPA entity (Consultation)
    │   └── repository/   Spring Data JPA repository
    ├── treatment/
    │   ├── entity/       JPA entity (Treatment)
    │   └── repository/   Spring Data JPA repository
    └── auth/             (placeholder)
```
