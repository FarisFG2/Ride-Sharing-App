# Software Requirements Specification (SRS) for Ride-Sharing Application

## 1. Introduction

### 1.1 Purpose

This Software Requirements Specification (SRS) document describes the functional and non-functional requirements for a ride-sharing application. The application will connect riders with drivers, facilitate ride bookings, handle payments, and provide administrative oversight.

### 1.2 Scope

The ride-sharing application will include:

- User registration and authentication
- Role-based access control (Admin, Driver, Rider)
- Ride booking and management
- Payment processing
- Notification system
- Administrative dashboard
- Microservices architecture
- Docker containerization
- Object Constraint Language (OCL) specifications
- Entity Relationship Diagram (ERD)

### 1.3 Definitions, Acronyms, and Abbreviations

- API: Application Programming Interface
- AOP: Aspect-Oriented Programming
- JWT: JSON Web Token
- REST: Representational State Transfer
- SRS: Software Requirements Specification
- UI: User Interface

## 2. Overall Description

### 2.1 Product Perspective

The ride-sharing application is a web-based platform that connects riders seeking transportation with drivers willing to provide rides. It consists of a backend built with Spring Boot microservices and a frontend built with React.

### 2.2 Product Functions

- User Management: Registration, authentication, profile management
- Ride Management: Booking, tracking, completion
- Payment Processing: Secure payment handling
- Notification System: Email and SMS notifications
- Administrative Functions: User management, ride oversight, analytics

### 2.3 User Characteristics

- **Rider**: End-user who books rides
- **Driver**: Service provider who offers rides
- **Admin**: System administrator who manages the platform

### 2.4 Constraints

- Backend must use Spring Boot with REST APIs
- Frontend can use any technology (React chosen)
- Must implement microservices architecture with Spring Cloud
- Must use AOP for cross-cutting concerns
- Must be Dockerized
- Must include database integration
- Must implement role-based authorization

## 3. Specific Requirements

### 3.1 External Interface Requirements

#### 3.1.1 User Interfaces

- Web-based frontend for riders and drivers
- Administrative web dashboard
- Mobile-responsive design

#### 3.1.2 Hardware Interfaces

- Standard web server hardware
- Database server
- Docker container runtime

#### 3.1.3 Software Interfaces

- Spring Boot framework
- Spring Cloud for microservices
- Database (MySQL/PostgreSQL)
- Docker for containerization
- React for frontend

### 3.2 Functional Requirements

#### 3.2.1 User Management Module

- **FR1.1**: System shall allow user registration with email, password, and role selection
- **FR1.2**: System shall authenticate users using JWT tokens
- **FR1.3**: System shall authorize API access based on user roles
- **FR1.4**: System shall allow profile updates for authenticated users

#### 3.2.2 Ride Management Module

- **FR2.1**: System shall allow riders to search for available rides
- **FR2.2**: System shall allow riders to book rides
- **FR2.3**: System shall allow drivers to accept/reject ride requests
- **FR2.4**: System shall track ride status (requested, accepted, in-progress, completed)
- **FR2.5**: System shall calculate and display ride fares

#### 3.2.3 Payment Module

- **FR3.1**: System shall process payments securely
- **FR3.2**: System shall handle different payment methods
- **FR3.3**: System shall generate payment receipts
- **FR3.4**: System shall handle payment failures and refunds

#### 3.2.4 Notification Module

- **FR4.1**: System shall send email notifications for important events
- **FR4.2**: System shall send SMS notifications for ride updates
- **FR4.3**: System shall allow users to manage notification preferences

#### 3.2.5 Administrative Module

- **FR5.1**: System shall provide user management capabilities for admins
- **FR5.2**: System shall provide ride monitoring and management
- **FR5.3**: System shall generate system reports and analytics

### 3.3 Non-Functional Requirements

#### 3.3.1 Performance

- **NFR1.1**: System shall respond to API requests within 2 seconds
- **NFR1.2**: System shall support up to 1000 concurrent users

#### 3.3.2 Security

- **NFR2.1**: System shall use HTTPS for all communications
- **NFR2.2**: System shall encrypt sensitive data at rest
- **NFR2.3**: System shall implement proper authentication and authorization

#### 3.3.3 Reliability

- **NFR3.1**: System shall have 99.9% uptime
- **NFR3.2**: System shall implement proper error handling and logging

#### 3.3.4 Usability

- **NFR4.1**: System shall provide intuitive user interfaces
- **NFR4.2**: System shall support multiple languages

#### 3.3.5 Maintainability

- **NFR5.1**: Code shall follow Spring Boot best practices
- **NFR5.2**: System shall use AOP for logging and monitoring

## 4. System Architecture

### 4.1 Microservices Architecture

The system will be built using the following microservices:

- Discovery Service (Eureka)
- API Gateway (Spring Cloud Gateway)
- User Service
- Ride Service
- Payment Service
- Notification Service

### 4.2 Technology Stack

- Backend: Spring Boot, Spring Cloud
- Frontend: React
- Database: MySQL
- Containerization: Docker
- Orchestration: Docker Compose

## 5. Database Design

### 5.1 User Table

- user_id (Primary Key)
- email
- password (hashed)
- role (RIDER, DRIVER, ADMIN)
- first_name
- last_name
- phone_number
- created_at
- updated_at

### 5.2 Ride Table

- ride_id (Primary Key)
- rider_id (Foreign Key)
- driver_id (Foreign Key)
- pickup_location
- dropoff_location
- status
- fare
- created_at
- updated_at

### 5.3 Payment Table

- payment_id (Primary Key)
- ride_id (Foreign Key)
- amount
- payment_method
- status
- created_at

## 6. Assumptions and Dependencies

### 6.1 Assumptions

- Users have internet access
- Payment gateway integration is available
- SMS and email services are available

### 6.2 Dependencies

- Spring Boot framework
- MySQL database
- Docker runtime
- Payment gateway API
- Email/SMS service APIs

## 7. Appendices

### 7.1 Use Case Diagram

See diagrams/use-case-diagram.mmd

### 7.2 Class Diagram

See diagrams/class-diagram.mmd

### 7.3 Sequence Diagram

See diagrams/sequence-diagram.mmd

### 7.4 Activity Diagram

See diagrams/activity-diagram.mmd
