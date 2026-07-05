# Revol Store Backend API

This is the backend API for **Revol Store**, an entertainment platform managing movies (via TMDb API) and games (local content).

## Features

- **Movie Management**: Fetches and syncs data from TMDb (popular, top-rated, upcoming, now playing).
- **Game Management**: Admin-driven catalog of downloadable games (PC, PlayStation, etc.).
- **User System**: JWT-based authentication, user roles, profile management.
- **Social Features**: Reviews, ratings, watchlists, and favorites.
- **Admin Dashboard**: Analytics and manual triggers for syncing data.

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.5** (Web, Security, Data JPA, WebFlux)
- **MySQL 8.0**
- **Flyway** (Database migrations)
- **MapStruct** (Object mapping)
- **Bucket4j** (API rate limiting)
- **Caffeine** (Caching)
- **Swagger / OpenAPI** (API documentation)
- **Docker & Docker Compose** (Containerization)

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker and Docker Compose
- TMDb API Key

## Setup & Running

1. **Environment Variables**:
   Copy the example environment file and update your details.
   ```bash
   cp .env .env
   ```
   Add your `TMDB_API_KEY` to the `.env` file.

2. **Run with Docker Compose**:
   ```bash
   docker-compose up -d
   ```
   This will start both the MySQL database and the Spring Boot application.

3. **Run Locally (Development)**:
   Start the database only:
   ```bash
   docker-compose up -d db
   ```
   Then run the Spring Boot app:
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
   ```

## Seed Data

The database is automatically seeded via Flyway migrations (`V2`, `V3`, `V4`):
- **Admin User**: `admin` / `admin123`
- **Normal User**: `user` / `user123`
- Pre-populated dummy games are added during initialization.

## API Documentation

Once the application is running, you can access the Swagger UI:
- **URL**: `http://localhost:8080/swagger-ui/index.html`

See `API_DOCUMENTATION.md` for a comprehensive overview of the endpoints.

## Testing

Run unit tests:
```bash
./mvnw test
```
"# revol-store" 
