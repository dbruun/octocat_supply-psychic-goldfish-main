# README for OctoCAT Supply Chain Management API - Java/Spring Boot

## Overview

Java/Spring Boot implementation of the OctoCAT Supply Chain Management API using SQLite as the database.

## Technology Stack

- **Java 21**
- **Spring Boot 3.2.1**
- **Spring Data JPA**
- **SQLite** (in-memory for development, file-based for production)
- **SpringDoc OpenAPI** (Swagger UI)
- **Maven** for dependency management
- **Lombok** for reducing boilerplate code

## Prerequisites

- Java 21 or higher
- Maven 3.8+ (or use the included Maven wrapper `./mvnw`)

## Getting Started

### Install Dependencies

```bash
make install
# or
./mvnw clean install
```

### Run Development Server

```bash
make dev
# or
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

The API will be available at `http://localhost:3000`.

### Initialize and Seed Database

```bash
make db-seed
# or
./mvnw spring-boot:run -Dspring-boot.run.arguments="--init-db --seed-db"
```

### Build for Production

```bash
make build
# or
./mvnw clean package
```

### Run Tests

```bash
make test
# or
./mvnw test
```

## API Documentation

Once the application is running, access the Swagger UI at:
- http://localhost:3000/api-docs

OpenAPI JSON specification:
- http://localhost:3000/api-docs.json

## Project Structure

```
api-java/
├── src/
│   ├── main/
│   │   ├── java/com/github/octocatsupply/
│   │   │   ├── OctocatSupplyApplication.java  # Main application
│   │   │   ├── config/                         # Configuration classes
│   │   │   ├── controller/                     # REST controllers
│   │   │   ├── model/                          # JPA entities
│   │   │   ├── repository/                     # JPA repositories
│   │   │   ├── service/                        # Business logic
│   │   │   ├── dto/                            # Data Transfer Objects
│   │   │   ├── exception/                      # Custom exceptions
│   │   │   └── util/                           # Utility classes
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-dev.properties
│   └── test/
│       └── java/com/github/octocatsupply/
├── database/
│   ├── migrations/                             # SQL migration files
│   └── seed/                                   # SQL seed data files
├── pom.xml                                     # Maven configuration
├── Makefile                                    # Build shortcuts
└── Dockerfile                                  # Container image definition
```

## Docker

### Build Image

```bash
docker build -t octocat-supply-api:java .
```

### Run Container

```bash
docker run -p 3000:3000 octocat-supply-api:java
```

## Environment Variables

- `PORT`: Server port (default: 3000)
- `DATABASE_PATH`: SQLite database file path (default: /app/data/supply_chain.db)
- `API_CORS_ORIGINS`: Comma-separated list of allowed CORS origins
- `SPRING_PROFILES_ACTIVE`: Active Spring profile (dev, production)

## REST API Endpoints

All endpoints are prefixed with `/api`.

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Suppliers
- `GET /api/suppliers` - Get all suppliers
- `GET /api/suppliers/{id}` - Get supplier by ID
- `POST /api/suppliers` - Create new supplier
- `PUT /api/suppliers/{id}` - Update supplier
- `DELETE /api/suppliers/{id}` - Delete supplier

### Orders
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create new order
- `PUT /api/orders/{id}` - Update order
- `DELETE /api/orders/{id}` - Delete order

### Branches
- `GET /api/branches` - Get all branches
- `GET /api/branches/{id}` - Get branch by ID
- `POST /api/branches` - Create new branch
- `PUT /api/branches/{id}` - Update branch
- `DELETE /api/branches/{id}` - Delete branch

### Headquarters
- `GET /api/headquarters` - Get all headquarters
- `GET /api/headquarters/{id}` - Get headquarters by ID
- `POST /api/headquarters` - Create new headquarters
- `PUT /api/headquarters/{id}` - Update headquarters
- `DELETE /api/headquarters/{id}` - Delete headquarters

### Deliveries
- `GET /api/deliveries` - Get all deliveries
- `GET /api/deliveries/{id}` - Get delivery by ID
- `POST /api/deliveries` - Create new delivery
- `PUT /api/deliveries/{id}` - Update delivery
- `DELETE /api/deliveries/{id}` - Delete delivery

### Order Details
- `GET /api/order-details` - Get all order details
- `GET /api/order-details/{id}` - Get order detail by ID
- `POST /api/order-details` - Create new order detail
- `PUT /api/order-details/{id}` - Update order detail
- `DELETE /api/order-details/{id}` - Delete order detail

### Order Detail Deliveries
- `GET /api/order-detail-deliveries` - Get all order detail deliveries
- `GET /api/order-detail-deliveries/{id}` - Get order detail delivery by ID
- `POST /api/order-detail-deliveries` - Create new order detail delivery
- `PUT /api/order-detail-deliveries/{id}` - Update order detail delivery
- `DELETE /api/order-detail-deliveries/{id}` - Delete order detail delivery

## Contributing

See the main repository CONTRIBUTING.md for contribution guidelines.

## License

MIT
