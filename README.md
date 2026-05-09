# Ride-Sharing Application

A comprehensive ride-sharing application built with Spring Boot microservices, featuring user authentication, ride booking, payment processing, and notifications.

## Features

- **Microservices Architecture**: Built with Spring Cloud and Netflix Eureka
- **User Management**: Registration, authentication with JWT, role-based access control
- **Ride Management**: Book rides, track status, fare calculation
- **Payment Processing**: Secure payment handling with multiple methods
- **Notification System**: Email and SMS notifications
- **API Gateway**: Centralized API management with Spring Cloud Gateway
- **Docker Containerization**: Fully containerized for easy deployment
- **Aspect-Oriented Programming**: Logging and monitoring with AOP
- **Design Patterns**: Service/Repository structure plus AOP for cross-cutting concerns
- **Object Constraint Language (OCL)**: Domain constraints are documented in `srs/OCL.md`
- **Academic Artifacts**: Includes SRS and system diagrams (Use Case, Activity, Sequence, Class, ERD)

## Architecture

The application consists of the following microservices:

1. **Discovery Service** (Port 8761): Eureka server for service discovery
2. **API Gateway** (Port 8080): Routes requests to appropriate services
3. **User Service** (Port 8081): Handles user registration, authentication, and profiles
4. **Ride Service** (Port 8082): Manages ride bookings and tracking
5. **Payment Service** (Port 8083): Processes payments
6. **Notification Service** (Port 8084): Sends notifications via email/SMS
7. **Frontend** (Port 3000): React-based user interface

## Prerequisites

- Docker and Docker Compose
- Java 17
- Node.js 18 (for frontend development)

## Quick Start

1. Clone the repository
2. Navigate to the project root
3. Run `docker-compose up --build`
4. Access the application:
   - Frontend: http://localhost:3000
   - API Gateway: http://localhost:8080
   - Eureka Dashboard: http://localhost:8761

## API Endpoints

### User Service

- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/users` - Get all users (Admin only)
- `GET /api/users/{id}` - Get user by ID

### Ride Service

- `POST /api/rides` - Book a ride
- `GET /api/rides/{id}` - Get ride details
- `GET /api/rides/rider/{riderId}` - Get rides by rider
- `PUT /api/rides/{id}/accept` - Accept ride (Driver)
- `PUT /api/rides/{id}/status` - Update ride status

### Payment Service

- `POST /api/payments` - Process payment
- `GET /api/payments/{id}` - Get payment details
- `GET /api/payments/ride/{rideId}` - Get payments by ride

### Notification Service

- `POST /api/notifications/email` - Send email
- `POST /api/notifications/sms` - Send SMS
- `GET /api/notifications/user/{userId}` - Get user notifications

## User Roles

- **RIDER**: Can book rides and view their history
- **DRIVER**: Can accept rides and manage their earnings
- **ADMIN**: Full access to user management and system monitoring

## Database

The application uses MySQL with separate databases for each service:

- userdb
- ridedb
- paymentdb
- notificationdb

## Development

### Building the Backend

From the project root, run:

```bash
./mvnw.cmd clean package   # Windows
./mvnw clean package      # macOS/Linux
```

### Building Individual Services

```bash
cd microservices/{service-name}
mvn clean package
```

### Running Services Locally

Each service can be run independently with proper configuration.

### Frontend Development

```bash
cd frontend
npm install
npm start
```

## Testing

Run tests for individual services:

```bash
cd microservices/{service-name}
mvn test
```

## Deployment

The application is fully containerized. Use Docker Compose for deployment:

```bash
docker-compose up -d
```

## Security

- JWT-based authentication
- Role-based authorization
- Password encryption with BCrypt
- HTTPS recommended for production

## Monitoring

- AOP-based logging for all service methods
- Eureka dashboard for service health monitoring

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.
