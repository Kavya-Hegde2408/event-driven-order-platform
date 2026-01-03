# Event-Driven Order Platform

## Architecture
- API Gateway
- Auth Service (JWT)
- Order Service
- Payment Service
- Inventory Service
- Loyalty Service
- Kafka for async communication

## Flow
1. Client authenticates → JWT
2. Order created via Gateway
3. Inventory reserved (event-driven)
4. Payment processed asynchronously
5. Loyalty points awarded
6. Failures handled via retry + DLQ

## Key Concepts Implemented
- Event-driven architecture
- Asynchronous processing
- Kafka retry & DLQ
- Idempotent consumers
- API Gateway security
- JWT authentication
- Dockerized microservices

## Failure Handling
- Kafka retries enabled
- DLQ for poison messages
- Idempotent consumers prevent duplication

## Security
- JWT based authentication
- Gateway-level authorization
- No service-to-service coupling

## Tech Stack
- Java 17
- Spring Boot
- Spring Cloud Gateway
- Kafka
- Docker & Docker Compose
