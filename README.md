# Motocycle

`Motocycle` is a Spring Boot backend for a motorcycle garage application. The project helps store motorcycles, manage service history, monitor maintenance needs, and control access through JWT authentication and role-based authorization.

## Project Description

The application is built around three main entities:

- `User` registers, confirms email, logs in, and works with personal motorcycles.
- `Motorcycle` stores basic information about a bike: mark, model, year, engine capacity, type, and mileage.
- `ServiceRecord` stores maintenance history with mileage, cost, date, and type of work.

The backend is suitable for a pet project, portfolio, or the basis of a garage management service where an owner can keep maintenance history in one place and an administrator can manage data centrally.

## Main Features

- Registration with email verification.
- Login with JWT token issuance.
- Protection of private routes through `Authorization: Bearer <token>`.
- Separation of roles between regular user and administrator.
- Storage and filtering of user motorcycles.
- Service history for each motorcycle.
- Maintenance reminder logic based on mileage and date of the last service.
- Expense analytics for motorcycle service records.
- Global API error handling with a unified response format.

## Technology Stack

- Java 21
- Spring Boot 4.0.1
- Spring Web MVC
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT (`jjwt`)
- Spring Mail
- Thymeleaf for email templates
- Maven


## Project Structure

```text
src/main/java/Garage/Motorcycle
|- controller           REST controllers
|- services             business logic
|- db                   JPA entities and repositories
|- web                  security, JWT filter, exception handling
|- mail                 email verification context and templates
|- MotocycleClass       motorcycle DTOs and enums
|- serviceRecordClass   service record DTOs, filters, analytics
|- userClass            user DTOs and roles
```



## Authentication Flow

1. Register a user through `POST /auth/register`.
2. The backend sends a verification email.
3. Confirm the account through `GET /auth/verify?token=...`.
4. Log in through `POST /auth/login`.
5. Use the returned token in the `Authorization` header:

```http
Authorization: Bearer <jwt-token>
```

## API Overview

### Public Auth Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/register` | Register a new user |
| `GET` | `/auth/verify?token=...` | Confirm email address |
| `POST` | `/auth/login` | Log in and receive JWT |
| `DELETE` | `/auth/{id}` | Delete a user by id |

### User Endpoints

All endpoints below require JWT authentication.

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/motorcycles` | Get the current user's motorcycles with filters and pagination |
| `GET` | `/motorcycles/{id}` | Get one motorcycle by id |
| `GET` | `/motorcycles/{motorcycleId}/service-record` | Get service history for a motorcycle |
| `GET` | `/motorcycles/{motorcycleId}/service-record/check` | Get maintenance reminders |
| `GET` | `/motorcycles/{motorcycleId}/service-record/analytics` | Get service cost analytics |

### Admin Endpoints

All admin endpoints require a token with role `ROLE_ADMIN`.

| Method   | Endpoint                                                                      | Description               |
|----------|-------------------------------------------------------------------------------|---------------------------|
| `GET`    | `/admin/users`                                                                | Get paginated user list   |
| `DELETE` | `/admin/users/{userId}`                                                       | Delete user               |
| `POST`   | `/admin/user/{userId}/motorcycles`                                            | Add motorcycle for a user |
| `PUT`    | `/admin/user/{userId}/motorcycles/{id}`                                       | Edit motorcycle           |
| `DELETE` | `/admin/user/{userId}/motorcycles/{id}`                                       | Delete motorcycle         |
| `POST`   | `/admin/users/{userId}/motorcycles/{motorcycleId}/service-record`             | Add service record        |
| `DELETE` | `/admin/users/{userId}/motorcycles/{motorcycleId}/service-record/{serviceId}` | Delete service record     |

## Supported Filters and Query Parameters

### `GET /motorcycles`

Supported query parameters:

- `motorcycleType`
- `mark`
- `pageSize`
- `currentPage`
- `minYear`
- `maxYear`
- `minCc`
- `maxCc`
- `sort`

Pagination defaults:

- `pageSize = 3`
- `currentPage = 0`
- maximum allowed `pageSize = 20`

Supported values for `sort`:

- `YEAR`
- `ENGINE_CC`
- `MOTORCYCLE_TYPE`
- `MARK`
- `MILEAGE`
- `MODEL`

### `GET /motorcycles/{motorcycleId}/service-record`

Supported query parameters:

- `serviceRecordType`
- `pageSize`
- `currentPage`

### `GET /motorcycles/{motorcycleId}/service-record/analytics`

Supported query parameters:

- `startDate`
- `endDate`

Date values are expected as `LocalDateTime`, for example:

```text
2026-04-27T10:30:00
```

## Enums Used by the API

### `MotorcycleType`

- `SPORT`
- `ENDURO`
- `CHOPPER`
- `NAKED`
- `SCOOTER`

### `ServiceRecordType`

- `OIL`
- `CHAIN`
- `TIRES`
- `FLUIDS`
- `BRAKES`
- `COSMETIC`

## Request Examples

### Register User

```http
POST /auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "strongPassword123"
}
```

### Login

```http
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "strongPassword123"
}
```

### Create Motorcycle as Admin

```http
POST /admin/user/1/motorcycles
Authorization: Bearer <admin-token>
Content-Type: application/json

{
  "id": null,
  "mark": "Honda",
  "model": "CBR600RR",
  "year": 2020,
  "mileage": 12000,
  "engineCc": 600,
  "motorcycleType": "SPORT"
}
```

### Create Service Record as Admin

```http
POST /admin/users/1/motorcycles/2/service-record
Authorization: Bearer <admin-token>
Content-Type: application/json

{
  "id": null,
  "serviceTime": null,
  "serviceRecordType": "OIL",
  "mileage": 12500,
  "comment": "Oil and filter changed",
  "price": 95.50
}
```

## Error Response Format

The backend returns errors in a unified format:

```json
{
  "msg": "Invalid page size",
  "errorMessage": "Page size must be between 1 and 20",
  "errorTime": "2026-04-27T12:15:30.000"
}
```


## Development Ideas

- No ideas yet😁
## Summary

This project is a solid backend foundation for a motorcycle garage service. It already includes authentication, email verification, role-based access, service history, maintenance checks, and service cost analytics. With API documentation, tests, and deployment packaging, it can grow into a fully usable production-style application.
