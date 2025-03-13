# Room Booking and Event Management System

## Overview
The **Room Booking and Event Management System** is a microservices-based application designed to facilitate room reservations and event scheduling. It provides a scalable and modular approach to handling user accounts, room availability, bookings, and approvals. The system is built using **Spring Boot**, **PostgreSQL**, and **Docker**, ensuring reliability and ease of deployment.

## Features
- **User Service**: Manages user registration, authentication, and role-based access control.
- **Room Service**: Maintains information about available rooms and their statuses.
- **Booking Service**: Handles reservations, cancellations, and availability checks.
- **Event Service**: Allows users to create and manage events.
- **Approval Service**: Facilitates approval workflows for room bookings and events.

## Technologies Used
- **Spring Boot** (Java)
- **PostgreSQL** (Relational Database Management System)
- **Docker & Docker Compose** (Containerization)
- **Postman & pgAdmin** (API Testing & Database Management)
- **RESTful APIs** for communication between services
- **Microservices Architecture** with independent service management

## System Architecture
The system consists of five microservices:
1. **UserService**: Handles user authentication and CRUD operations.
2. **RoomService**: Maintains room details and availability.
3. **BookingService**: Manages booking requests and ensures room availability.
4. **EventService**: Supports event scheduling and modifications.
5. **ApprovalService**: Administers booking approvals and validations.

Each microservice operates independently and communicates via REST APIs.

## Installation & Setup
### Prerequisites
Ensure you have the following installed:
- Java (JDK 22)
- Docker & Docker Compose
- PostgreSQL
- Maven

### Running the Services
1. Clone the repositories:
   ```sh
   git clone https://github.com/yourusername/UserService.git
   git clone https://github.com/yourusername/RoomService.git
   git clone https://github.com/yourusername/BookingService.git
   git clone https://github.com/yourusername/EventService.git
   git clone https://github.com/yourusername/ApprovalService.git
   ```
2. Navigate to each service directory and build the project:
   ```sh
   mvn clean install
   ```
3. Start PostgreSQL using Docker:
   ```sh
   docker-compose up -d
   ```
4. Run each microservice:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints
### UserService
- `POST /api/users/register` – Register a new user
- `POST /api/users/login` – Authenticate user
- `GET /api/users/{id}` – Get user details

### RoomService
- `GET /api/rooms` – Retrieve available rooms
- `POST /api/rooms` – Add a new room

### BookingService
- `POST /api/bookings` – Create a booking request
- `GET /api/bookings/{id}` – Retrieve booking details

### EventService
- `POST /api/events` – Schedule a new event
- `GET /api/events` – Fetch all events

### ApprovalService
- `POST /api/approvals` – Approve or reject a booking
- `GET /api/approvals/{id}` – Retrieve approval status

## Testing
- Use **Postman** to test API endpoints.
- Access the database via **pgAdmin** for verification.
- Run unit tests using:
  ```sh
  mvn test
  ```

## Future Enhancements
- Implement **Kafka** for event-driven architecture.
- Introduce **GraphQL API** for optimized queries.
- Add **CI/CD pipeline** for automated deployments.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Contributors
- Kei Ishikawa (Lead Developer)
- [Other Team Members]

---
For any inquiries or contributions, feel free to open an issue or a pull request! 🚀

