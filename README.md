# Money Transfer System

An enterprise-grade digital money transfer microservice built progressively across training modules.

## Tech Stack

| Module | Technologies |
|--------|-------------|
| GIT | Git, GitHub |
| Advanced Java | Java 17, JUnit 5, Lombok, Bean Validation |
| Spring Boot | Spring Boot 3.x, Spring Data JPA, Spring Security, MySQL |
| Angular | Angular 18, TypeScript, Angular Material |
| Snowflake | Data warehouse, ETL, Analytics |

## Project Structure

```
money-transfer-system/
├── backend/          # Spring Boot REST API
├── frontend/         # Angular SPA
├── database/         # MySQL schema & seed data
├── snowflake/        # Snowflake analytics scripts
└── docs/             # Documentation
```

## Quick Start

### Prerequisites

- Java 17+
- Node.js 18+
- MySQL 8+
- Maven 3.8+

### Backend

```bash
cd backend
mvn spring-boot:run
```

Default: http://localhost:8080

### Frontend

```bash
cd frontend
npm install
ng serve
```

Default: http://localhost:4200

### Database

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p money_transfer < database/seed-data.sql
```

### Default Credentials

- Username: `user`
- Password: `password`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/v1/transfers | Execute fund transfer |
| GET | /api/v1/accounts/{id} | Get account details |
| GET | /api/v1/accounts/{id}/balance | Get account balance |
| GET | /api/v1/accounts/{id}/transactions | Get transaction history |

## License

MIT
