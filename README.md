# Country & City Registry API

## Tech Stack

- Java 21
- Spring Boot 3.2.2
- PostgreSQL 16
- Flyway (schema migrations)
- MapStruct (entity/DTO mapping)
- Lombok
- Swagger / OpenAPI (springdoc)
- Docker

## Prerequisites

- Java 21
- Maven
- Docker Desktop

## Getting Started

### 1. Start the database

```bash
docker compose up -d
```

This starts a PostgreSQL 16 instance on port `5432`. Flyway will automatically create the schema and tables on first startup.

### 2. Run the application

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080`.

### 3. Open Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

## API Endpoints

### Countries

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/countries?pageNumber=0&size=10` | Get paginated list of countries |
| GET | `/countries/{id}` | Get country by ID |
| POST | `/countries` | Create a new country |
| PUT | `/countries/{id}` | Update a country |
| DELETE | `/countries/{id}` | Delete a country |

### Cities

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/cities?pageNumber=0&size=10` | Get paginated list of cities |
| GET | `/cities/{id}` | Get city details by ID |
| GET | `/countries/{countryId}/cities?pageNumber=0&size=10` | Get cities by country (paginated) |
| POST | `/cities` | Create a new city |
| PUT | `/cities/{id}` | Update a city |
| DELETE | `/cities/{id}` | Delete a city |

## Request Examples

### Create a country
```json
POST /countries
{
  "name": "Luxembourg"
}
```

### Create a city
```json
POST /cities
{
  "name": "Luxembourg City",
  "population": 125000,
  "countryId": 1
}
```

## Pagination Response Format

All list endpoints return a paginated response:

```json
{
  "content": [...],
  "page": 0,
  "size": 10,
  "totalElements": 25,
  "totalPages": 3,
  "last": false
}
```

## Database

The database schema is managed by Flyway and created automatically on startup.

```
appdb (PostgreSQL)
└── interview (schema)
    ├── country
    │   ├── id   BIGINT (auto-generated)
    │   └── name VARCHAR(255)
    └── city
        ├── id         BIGINT (auto-generated)
        ├── name       VARCHAR(255)
        ├── population BIGINT
        └── country_id BIGINT (FK → country.id)
```

## Running Tests

```bash
mvn test
```

Tests use the same database with `@Transactional` rollback — each test runs in isolation and leaves no data behind.

## OpenAPI Specification

The OpenAPI contract is available at:
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Raw YAML: `http://localhost:8080/v3/api-docs.yaml`
- Contract file: `openapi.yaml` (project root)
