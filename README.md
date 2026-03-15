# Product Catalog REST API

This project is a simple REST API for managing a product catalog.

The application allows creating, updating, deleting and retrieving products with dynamic attributes and producer information.

The project is built using Spring Boot and uses Liquibase for database migrations.

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Liquibase
- H2 Database
- JUnit 5
- Mockito
- Maven

---

## Setup Instructions

### 1. Clone the repository

git clone <repository-url>
cd product-catalog

### 2. Build the project

mvn clean install

### 3. Run the application

mvn spring-boot:run

The application will start on:

http://localhost:8080

---

## Database

The application uses an H2 in-memory database.

The database schema is automatically created using Liquibase migrations on application startup.

Liquibase creates the following tables:

producer  
product  
product_attributes  
DATABASECHANGELOG  
DATABASECHANGELOGLOCK  

Sample data is inserted automatically during startup.

---

## API Endpoints

### Get all products

GET /api/products

---

### Filter products by producer

GET /api/products?producerName=Samsung

---

### Create product

POST /api/products

Example request body:

{
  "name": "Samsung Galaxy S23",
  "description": "Samsung flagship smartphone",
  "price": 3499.99,
  "producer": {
    "name": "Samsung"
  },
  "otherAttributesMap": {
    "RAM": "8GB",
    "Storage": "256GB"
  }
}

---

### Update product

PUT /api/products/{id}

---

### Delete product

DELETE /api/products/{id}

---

## Running Tests

To run unit tests:

mvn test

Tests cover the service layer and include validation, CRUD operations and edge cases.

---

## Author

Wojciech Andrzejczak