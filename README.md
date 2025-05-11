# 📝 Journal App - Spring Boot 3.1.5

A Backend journal application built with Spring Boot 3.1.5, and Redis for caching. 
This app provides complete CRUD functionality for managing journal entries along with user management and security integration.
Its also have a feature of sentimental Analysis and sending email every 7th day of the week using scheduler.

---

## 🚀 Features

- ✅ User Registration and Login
- 📒 Create, Read, Update, Delete (CRUD) journal entries
- ⚡ Redis-based caching for performance
- 🔐 Secure endpoints using Spring Security
- 🌱 Configuration through `application.yml`

---

## 🛠️ Technologies

- Spring Boot 3.1.5
- Spring Security
- Spring Data JPA
- Redis
- mongoDB-cloud

---

## 🔄 API Endpoints

### 🧑 User API

| Method | Endpoint         | Description                | Auth Required |
|--------|------------------|----------------------------|----------------|
| `POST` | `/public/signup`  | Register a new user        | ❌             |
| `GET`  | `/api/user`      | Get current user profile   | ✅             |
| `PUT`  | `/api/user`      | Update current user info   | ✅             |
| `DELETE`| `/api/user`     | Delete current user        | ✅             |

### 📓 Journal API

| Method | Endpoint              | Description                  | Auth Required |
|--------|-----------------------|------------------------------|----------------|
| `GET`  | `/api/journals`       | List all journals for user   | ✅             |
| `POST` | `/api/journals`       | Create new journal entry     | ✅             |
| `GET`  | `/api/journals/{id}`  | Get journal by ID            | ✅             |
| `PUT`  | `/api/journals/{id}`  | Update journal entry         | ✅             |
| `DELETE`| `/api/journals/{id}` | Delete journal entry         | ✅             |

---

## ⚡ Redis Caching

The app uses Redis to cache journal data to improve read performance. Common usage:

- `@Cacheable("journals")` on read operations
- `@CacheEvict(value = "journals", allEntries = true)` on create/update/delete

You must have Redis running locally or remotely. By default, the app connects to `localhost:6379`.

---

## ⚙️ `application.yml` Configuration

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

  redis:
    host: localhost
    port: 6379

  thymeleaf:
    cache: false

logging:
  level:
    org.springframework: INFO
    com.journal: DEBUG
