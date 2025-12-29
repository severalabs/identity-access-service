# Identity & Access Service (IAS)

Identity & Access Service (IAS) is a backend service responsible for user authentication,
authorization, and role management. It serves as a foundational service for other
applications within Sedulous Veracity Labs.

## Tech Stack
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven

## Features (Planned)
- User registration
- JWT-based authentication
- Role-based access control
- Secure password storage
- Audit logging

## Getting Started

### Prerequisites
- Java 17+
- Maven
- PostgreSQL

### Running Locally
1. Clone the repository
2. Configure database credentials in `application.yml`
3. Run the application using: mvn spring-boot:run

The application will start on `http://localhost:8080`

## Project Structure
com.sedulousveracity.ias
├── config
├── controller
├── service
├── repository
├── domain
├── dto
├── security
├── exception


## Notes
This service is under active development and follows clean architecture
and best practices for enterprise backend systems.
