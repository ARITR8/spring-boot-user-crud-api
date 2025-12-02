# User CRUD API

A production-ready RESTful API for user management built with Spring Boot, following enterprise-grade architecture patterns and best practices.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Design Decisions](#design-decisions)
- [Security](#security)
- [Database Schema](#database-schema)
- [Development](#development)

---

## 🎯 Overview

This application provides a comprehensive user management system with full CRUD operations, following a layered architecture pattern that separates concerns and ensures maintainability, scalability, and testability.

### Purpose

The User CRUD API enables:

- User account creation and management
- Secure password handling with BCrypt hashing
- Soft delete functionality for data preservation
- Optimistic locking for concurrent access control
- Comprehensive validation and error handling
- RESTful API design following industry standards

### Key Capabilities

- **User Management**: Create, read, update, and delete user accounts
- **Data Integrity**: Unique constraints on email and username
- **Audit Trail**: Automatic timestamp tracking for all entities
- **Soft Delete**: Preserve data while marking accounts as inactive
- **Dynamic Queries**: JPA Specifications for flexible search and filtering
- **Type-Safe Mapping**: MapStruct for compile-time DTO conversion

---

## 🏗️ Architecture

### Layered Architecture

The application follows a clean, layered architecture pattern:

```
┌─────────────────────────────────────┐
│      Controller Layer (REST API)    │  ← HTTP endpoints, request/response handling
├─────────────────────────────────────┤
│      Service Layer (Business Logic) │  ← Business rules, validation, orchestration
├─────────────────────────────────────┤
│      Mapper Layer (DTO Conversion)  │  ← Entity ↔ DTO transformation
├─────────────────────────────────────┤
│   Repository Layer (Data Access)    │  ← Database operations, queries
├─────────────────────────────────────┤
│      Entity Layer (Domain Model)    │  ← JPA entities, domain objects
└─────────────────────────────────────┘
```

### Design Principles

- **Separation of Concerns**: Each layer has a single, well-defined responsibility
- **Dependency Inversion**: High-level modules depend on abstractions (interfaces)
- **Single Responsibility**: Each class has one reason to change
- **DRY (Don't Repeat Yourself)**: Reusable components and utilities
- **Security by Design**: Password hashing, input validation, exception handling

---

## 🛠️ Technology Stack

### Core Framework

- **Spring Boot 4.0.0** - Application framework and auto-configuration
- **Java 21** - Modern Java features and performance improvements

### Data Layer

- **Spring Data JPA** - Data access abstraction
- **Hibernate** - JPA implementation and ORM
- **H2 Database** - In-memory database for development
- **Flyway** - Database migration and version control

### Security

- **Spring Security** - Authentication and authorization framework
- **BCrypt** - Password hashing algorithm

### Mapping & Validation

- **MapStruct 1.5.5** - Compile-time DTO mapping (zero runtime overhead)
- **Jakarta Validation** - Bean validation framework
- **Hibernate Validator** - Validation implementation

### API Documentation

- **Springdoc OpenAPI 2.5.0** - OpenAPI 3.0 documentation generation

### Build & Development

- **Maven** - Dependency management and build tool
- **Lombok** - Boilerplate code reduction
- **Spring Boot Actuator** - Application monitoring and metrics

---

## ✨ Features

### Core Functionality

- ✅ **User CRUD Operations**: Create, read, update, and delete users
- ✅ **Pagination Support**: Efficient handling of large datasets
- ✅ **Dynamic Search**: JPA Specifications for flexible querying
- ✅ **Soft Delete**: Preserve data while marking as inactive
- ✅ **Optimistic Locking**: Prevent concurrent modification conflicts

### Security Features

- ✅ **Password Hashing**: BCrypt encryption for secure password storage
- ✅ **Input Validation**: Comprehensive validation on all inputs
- ✅ **Exception Handling**: Centralized error handling with proper HTTP status codes
- ✅ **Data Sanitization**: Password exclusion from responses and logs

### Quality Features

- ✅ **Type Safety**: Compile-time type checking with MapStruct
- ✅ **Audit Trail**: Automatic timestamp tracking (createdAt, updatedAt)
- ✅ **Null Safety**: Comprehensive null handling throughout the application
- ✅ **Documentation**: Extensive JavaDoc and inline comments

---

## 📁 Project Structure

```
usercrud/
├── src/
│   ├── main/
│   │   ├── java/com/example/practice/usercrud/
│   │   │   ├── config/                    # Configuration classes
│   │   │   │   ├── AuditingConfig.java    # JPA Auditing configuration
│   │   │   │   ├── JpaConfig.java         # JPA custom configuration
│   │   │   │   └── SecurityConfig.java   # Spring Security configuration
│   │   │   ├── controller/                # REST API endpoints
│   │   │   └── UserController.java        # User REST controller
│   │   │   ├── data/
│   │   │   │   ├── dto/                   # Data Transfer Objects
│   │   │   │   │   ├── request/           # Request DTOs
│   │   │   │   │   │   ├── UserRequest.java
│   │   │   │   │   │   └── UserUpdateRequest.java
│   │   │   │   │   └── response/          # Response DTOs
│   │   │   │   │       └── UserResponse.java
│   │   │   │   └── entity/                # JPA entities
│   │   │   │       ├── BaseEntity.java    # Base entity with common fields
│   │   │   │       └── User.java           # User entity
│   │   │   ├── exception/                 # Custom exceptions
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── UserNotFoundException.java
│   │   │   │   ├── UserAlreadyExistsException.java
│   │   │   │   └── ValidationException.java
│   │   │   ├── mapper/                     # DTO mapping
│   │   │   │   └── UserMapper.java        # MapStruct mapper interface
│   │   │   ├── repository/                 # Data access layer
│   │   │   │   ├── specification/          # JPA Specifications
│   │   │   │   │   └── UserSpecification.java
│   │   │   │   └── UserRepository.java    # Spring Data JPA repository
│   │   │   ├── service/                    # Business logic layer
│   │   │   │   ├── impl/
│   │   │   │   │   └── UserServiceImpl.java
│   │   │   │   └── UserService.java        # Service interface
│   │   │   └── UsercrudApplication.java    # Main application class
│   │   └── resources/
│   │       ├── application.properties      # Application configuration
│   │       └── db/migration/               # Flyway migrations
│   │           ├── V1__Create_users_table.sql
│   │           └── V2__Add_indexes.sql
│   └── test/                               # Test classes
└── pom.xml                                  # Maven configuration
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.6+** or use included Maven wrapper
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Installation

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd usercrud
   ```

2. **Build the project**

   ```bash
   mvn clean install
   ```

3. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

   Or run `UsercrudApplication.java` directly from your IDE.

4. **Verify the application is running**
   - Application starts on: `http://localhost:8086`
   - H2 Console: `http://localhost:8086/h2-console`
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: (empty)

### Configuration

Edit `src/main/resources/application.properties` to customize:

- Server port (default: 8086)
- Database configuration
- Logging levels
- Flyway settings

---

## 📚 API Documentation

### Base URL

```
http://localhost:8086/api/users
```

### Endpoints

#### Create User

```http
POST /api/users
Content-Type: application/json

{
  "email": "user@example.com",
  "username": "username",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response:** `201 Created`

```json
{
  "id": 1,
  "email": "user@example.com",
  "username": "username",
  "firstName": "John",
  "lastName": "Doe",
  "isActive": true,
  "createdAt": "2024-12-03T02:30:00.000Z",
  "updatedAt": "2024-12-03T02:30:00.000Z",
  "lastLoginAt": null
}
```

#### Get User by ID

```http
GET /api/users/{id}
```

**Response:** `200 OK` (UserResponse object)

#### Get User by Email

```http
GET /api/users/email/{email}
```

#### Get User by Username

```http
GET /api/users/username/{username}
```

#### Get All Users (Paginated)

```http
GET /api/users?page=0&size=20&sort=createdAt,desc
```

**Query Parameters:**

- `page` - Page number (default: 0)
- `size` - Page size (default: 20)
- `sort` - Sort criteria (e.g., `email,asc` or `createdAt,desc`)

#### Get All Users (List)

```http
GET /api/users/all
```

#### Update User

```http
PUT /api/users/{id}
Content-Type: application/json

{
  "email": "newemail@example.com",
  "firstName": "Updated",
  "lastName": "Name"
}
```

**Note:** Only non-null fields are updated. Password is optional.

#### Delete User (Soft Delete)

```http
DELETE /api/users/{id}
```

**Response:** `204 No Content`

#### Check Email Exists

```http
GET /api/users/exists/email/{email}
```

**Response:** `200 OK` (boolean)

#### Check Username Exists

```http
GET /api/users/exists/username/{username}
```

**Response:** `200 OK` (boolean)

### Error Responses

#### 400 Bad Request (Validation Error)

```json
{
  "timestamp": "2024-12-03T02:30:00.000Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "validationErrors": {
    "email": ["Email must be a valid email address"],
    "password": ["Password must be between 8 and 255 characters"]
  }
}
```

#### 404 Not Found

```json
{
  "timestamp": "2024-12-03T02:30:00.000Z",
  "status": 404,
  "error": "Not Found",
  "message": "User with ID 999 not found"
}
```

#### 409 Conflict

```json
{
  "timestamp": "2024-12-03T02:30:00.000Z",
  "status": 409,
  "error": "Conflict",
  "message": "User with email 'user@example.com' already exists"
}
```

---

## 🎨 Design Decisions

### Why MapStruct?

- **Zero Runtime Overhead**: Compile-time code generation, no reflection
- **Type Safety**: Compile-time error detection
- **Performance**: Generated code is as fast as hand-written mappers
- **Maintainability**: Automatic mapping reduces boilerplate

### Why JPA Specifications?

- **Dynamic Queries**: Build queries at runtime without method explosion
- **Composability**: Combine multiple criteria with AND/OR logic
- **Type Safety**: Compile-time query building
- **Reusability**: Share query logic across different use cases

### Why Soft Delete?

- **Data Preservation**: Maintain audit trail and referential integrity
- **Recovery**: Ability to restore accidentally deleted accounts
- **Compliance**: Meet data retention requirements
- **Analytics**: Historical data remains available

### Why Layered Architecture?

- **Separation of Concerns**: Each layer has a single responsibility
- **Testability**: Easy to mock dependencies and test in isolation
- **Maintainability**: Changes are localized to specific layers
- **Scalability**: Can scale different layers independently

### Why DTOs?

- **Security**: Hide internal entity structure (password, version)
- **API Versioning**: Different DTOs for different API versions
- **Performance**: Return only required fields
- **Decoupling**: API contract independent of database schema

---

## 🔒 Security

### Password Security

- Passwords are hashed using **BCrypt** before persistence
- Password field is **never** included in API responses
- Password is excluded from `toString()` and logging

### Input Validation

- **Jakarta Validation** annotations on all DTOs
- Server-side validation for all inputs
- Comprehensive error messages for validation failures

### Exception Handling

- Centralized exception handling via `GlobalExceptionHandler`
- Proper HTTP status codes for different error scenarios
- No sensitive information leaked in error messages

### Current Security Configuration

⚠️ **Note**: Current configuration allows all requests for development/testing. For production:

- Implement proper authentication (JWT, OAuth2)
- Configure role-based access control (RBAC)
- Enable CSRF protection
- Add rate limiting
- Implement API key authentication

---

## 🗄️ Database Schema

### Users Table

| Column          | Type         | Constraints                 | Description                |
| --------------- | ------------ | --------------------------- | -------------------------- |
| `id`            | BIGINT       | PRIMARY KEY, AUTO_INCREMENT | Unique identifier          |
| `created_at`    | TIMESTAMP    | NOT NULL                    | Creation timestamp         |
| `updated_at`    | TIMESTAMP    | NOT NULL                    | Last update timestamp      |
| `version`       | BIGINT       | NOT NULL, DEFAULT 0         | Optimistic locking version |
| `email`         | VARCHAR(255) | NOT NULL, UNIQUE            | Email address              |
| `username`      | VARCHAR(50)  | NOT NULL, UNIQUE            | Username                   |
| `password`      | VARCHAR(255) | NOT NULL                    | Hashed password            |
| `first_name`    | VARCHAR(100) | NULL                        | First name                 |
| `last_name`     | VARCHAR(100) | NULL                        | Last name                  |
| `is_active`     | BOOLEAN      | NOT NULL, DEFAULT TRUE      | Active status              |
| `last_login_at` | TIMESTAMP    | NULL                        | Last login timestamp       |

### Indexes

- `idx_users_email` - Unique index on email
- `idx_users_username` - Unique index on username
- `idx_users_active` - Index on is_active for filtering

---

## 💻 Development

### Building the Project

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package application
mvn package

# Run application
mvn spring-boot:run
```

### Code Generation

- **MapStruct**: Generates `UserMapperImpl` during compilation
- **Lombok**: Generates getters, setters, builders at compile time
- Generated files are in: `target/generated-sources/annotations/`

### IDE Setup

1. Enable annotation processing in your IDE
2. Mark `target/generated-sources/annotations` as generated sources
3. Install Lombok plugin (if using IntelliJ/Eclipse)

### Testing the API

**Using curl:**

```bash
# Create user
curl -X POST http://localhost:8086/api/users \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","username":"testuser","password":"password123"}'

# Get user
curl http://localhost:8086/api/users/1

# Get all users
curl http://localhost:8086/api/users?page=0&size=10
```

**Using Postman:**

- Import the API endpoints
- Use the examples provided in [API Documentation](#api-documentation)

---

## 📝 Best Practices Implemented

✅ **Layered Architecture** - Clear separation of concerns  
✅ **Interface-Based Design** - Dependencies on abstractions  
✅ **DTO Pattern** - Separate API contracts from entities  
✅ **Exception Handling** - Centralized error handling  
✅ **Input Validation** - Comprehensive validation at all layers  
✅ **Password Security** - BCrypt hashing, never exposed  
✅ **Soft Delete** - Data preservation and audit trail  
✅ **Optimistic Locking** - Concurrent access control  
✅ **Pagination** - Efficient handling of large datasets  
✅ **Type Safety** - Compile-time type checking  
✅ **Documentation** - Extensive JavaDoc and comments  
✅ **Null Safety** - Comprehensive null handling

---

## 🚧 Future Enhancements

- [ ] JWT-based authentication
- [ ] Role-based access control (RBAC)
- [ ] Email verification
- [ ] Password reset functionality
- [ ] User profile image upload
- [ ] Advanced search with full-text indexing
- [ ] Rate limiting
- [ ] API versioning
- [ ] Comprehensive unit and integration tests
- [ ] Docker containerization
- [ ] CI/CD pipeline
- [ ] Production database configuration (PostgreSQL/MySQL)

---

## 📄 License

This project is for educational and practice purposes.

---

## 👤 Author

Aritra Das

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- MapStruct for efficient DTO mapping
- The open-source community for amazing tools and libraries
