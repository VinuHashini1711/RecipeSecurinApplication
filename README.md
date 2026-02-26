This is a Spring Boot backend application that manages recipe data.
It allows users to upload recipe data using a JSON file, store it in PostgreSQL, and retrieve recipes using REST APIs.

Project Architecture

Controller → Service → Repository → Database

- Controller: Handles HTTP requests
- Service: Contains business logic
- Repository: Handles database operations
- Entity: Represents database table
- DTO: Used for API data transfer
