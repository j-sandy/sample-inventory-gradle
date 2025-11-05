# Sample Inventory Gradle API

## Description
A backend REST API project developed in Java using Spring Boot and Gradle (Groovy DSL) to manage the inventory of an e-commerce website. It uses an in-memory H2 database for data storage and Spring Security for authentication and session management.

## Features
*   User authentication and session management.
*   CRUD operations for inventory items (add, update, delete).
*   Flexible item search based on various criteria (item code, procurement date, expiry date, name).
*   In-memory H2 database for easy setup and development.

## Technologies Used
*   Java 17
*   Spring Boot 3.2.0
*   Gradle 8.5 (Groovy DSL)
*   Spring Data JPA
*   Spring Security
*   H2 Database (in-memory)
*   Lombok
*   Jakarta Validation

## Setup Instructions

1.  **Prerequisites:**
    *   Java Development Kit (JDK) 17 or higher.
    *   Gradle 8.5 (or use the provided Gradle Wrapper).
    *   Git.

2.  **Clone the Repository:**
    ```bash
    git clone https://github.com/j-sandy/sample-inventory-gradle.git
    cd sample-inventory-gradle
    ```

## Building the Project

To build the project, including compiling code and running tests:
```bash
./gradlew build
```
This will generate an executable JAR file in the `build/libs/` directory.

## Running the Application

To start the Spring Boot application:
```bash
./gradlew bootRun
```
The application will start on `http://localhost:8080`.

## API Documentation

All API endpoints require authentication. The default user credentials are:
*   **Username:** `user`
*   **Password:** `password`

You will need to obtain a `JSESSIONID` cookie and include an `Authorization: Basic dXNlcjpwYXNzd29yZA==` header (where `dXNlcjpwYXNzd29yZA==` is the base64 encoding of `user:password`) in all authenticated requests.

### 1. Authenticate and Start Session (POST /api/auth/login)
*   **Description:** Authenticates a user and establishes a session.
*   **Request:**
    ```bash
    curl -v -X POST http://localhost:8080/api/auth/login -u user:password
    ```
*   **Response:** Look for `Set-Cookie: JSESSIONID=YOUR_SESSION_ID_HERE` in the headers.

### 2. Logout and End Session (POST /api/auth/logout)
*   **Description:** Invalidates the current session.
*   **Request:**
    ```bash
    curl -v -X POST http://localhost:8080/api/auth/logout \
    -H "Cookie: JSESSIONID=YOUR_SESSION_ID_HERE" \
    -H "Authorization: Basic dXNlcjpwYXNzd29yZA=="
    ```

### 3. Add Item Details (POST /api/items)
*   **Description:** Adds a new inventory item.
*   **Request:**
    ```bash
    curl -v -X POST http://localhost:8080/api/items \
    -H "Content-Type: application/json" \
    -H "Cookie: JSESSIONID=YOUR_SESSION_ID_HERE" \
    -H "Authorization: Basic dXNlcjpwYXNzd29yZA==" \
    -d '{
        "name": "Laptop",
        "itemCode": "LT001",
        "description": "Gaming Laptop",
        "quantity": 10,
        "procurementDate": "2025-10-20"
    }'
    ```

### 4. Update Item Details (PUT /api/items/{itemCode})
*   **Description:** Updates an existing inventory item by its item code.
*   **Request:**
    ```bash
    curl -v -X PUT http://localhost:8080/api/items/LT001 \
    -H "Content-Type: application/json" \
    -H "Cookie: JSESSIONID=YOUR_SESSION_ID_HERE" \
    -H "Authorization: Basic dXNlcjpwYXNzd29yZA==" \
    -d '{
        "name": "Laptop",
        "itemCode": "LT001",
        "description": "Updated Gaming Laptop with more RAM",
        "quantity": 15,
        "procurementDate": "2025-10-20"
    }'
    ```

### 5. Delete Item (DELETE /api/items/{itemCode})
*   **Description:** Deletes an inventory item by its item code.
*   **Request:**
    ```bash
    curl -v -X DELETE http://localhost:8080/api/items/LT001 \
    -H "Cookie: JSESSIONID=YOUR_SESSION_ID_HERE" \
    -H "Authorization: Basic dXNlcjpwYXNzd29yZA=="
    ```

### 6. Fetch Item Details (GET /api/items/search)
*   **Description:** Fetches item details based on item code, procurement date, expiry date, or name.
*   **Request (by itemCode):**
    ```bash
    curl -v -X GET "http://localhost:8080/api/items/search?itemCode=LT001" \
    -H "Cookie: JSESSIONID=YOUR_SESSION_ID_HERE" \
    -H "Authorization: Basic dXNlcjpwYXNzd29yZA=="
    ```
*   **Request (by name):**
    ```bash
    curl -v -X GET "http://localhost:8080/api/items/search?name=Laptop" \
    -H "Cookie: JSESSIONID=YOUR_SESSION_ID_HERE" \
    -H "Authorization: Basic dXNlcjpwYXNzd29yZA=="
