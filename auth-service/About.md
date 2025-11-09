# 🔐 ShutterLink Auth Service

The **Auth Service** is the authentication and authorization microservice of the **ShutterLink** ecosystem.  
It handles user registration, login, JWT token generation, validation, and inter-service authentication.

---

## 🧠 Overview

This service manages:
- User registration and secure password hashing  
- Authentication via JWT tokens (access + refresh)  
- Token validation for other microservices  
- User role management (`USER`, `ADMIN`)  
- Kafka event publishing on key user actions (e.g., registration)  

---

## 🧩 Architecture

```mermaid
flowchart LR
    subgraph Client
        A[User / Frontend App]
    end

    subgraph API_Gateway
        B[Spring Cloud Gateway]
    end

    subgraph Auth_Service
        C1[Controller]
        C2[Service Layer]
        C3[Repository]
        C4[JwtUtil]
        DB[(PostgreSQL)]
    end

    subgraph Other_Services
        D1[User Service]
        D2[Content Service]
        D3[Notification Service]
    end

    subgraph Kafka
        K[(user.events Topic)]
    end

    A -->|HTTP Requests| B
    B -->|/auth/* routes| C1
    C1 --> C2 --> C3 --> DB
    C2 --> C4

    D1 -->|Validate Token| C1
    D2 -->|Validate Token| C1
    D3 -->|Listen for Events| K

    C2 -->|Publish USER_REGISTERED| K
