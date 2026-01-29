# Smart Parking System - Architecture Documentation

## 📐 System Architecture Overview

This document provides detailed information about the architectural decisions, design patterns, and technical implementation of the Smart Parking Management System.

---

## 🏗️ Architecture Pattern: Layered Architecture

The application follows a **4-tier layered architecture** pattern:

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                        │
│                   (REST Controllers)                         │
│   - UserController, VehicleController, ReservationController│
│   - Request/Response handling                                │
│   - HTTP mapping and validation                              │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│                     SERVICE LAYER                            │
│                  (Business Logic)                            │
│   - UserService, ReservationService, PaymentService         │
│   - Price calculation, validations                           │
│   - Transaction management                                   │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│                   REPOSITORY LAYER                           │
│                 (Data Access Layer)                          │
│   - JPA Repositories                                         │
│   - Custom queries                                           │
│   - Database operations                                      │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│                    DATABASE LAYER                            │
│                  (H2 / PostgreSQL)                           │
│   - Entity storage                                           │
│   - Relationship management                                  │
│   - Data persistence                                         │
└─────────────────────────────────────────────────────────────┘
```

---

## 📦 Package Structure

```
com.example.demo/
│
├── entity/                     # Domain Models (JPA Entities)
│   ├── User.java
│   ├── Vehicle.java
│   ├── ParkingLot.java
│   ├── ParkingSpot.java
│   ├── Reservation.java
│   ├── Payment.java
│   └── Subscription.java
│
├── dto/                        # Data Transfer Objects
│   ├── UserDTO.java
│   ├── VehicleDTO.java
│   ├── ParkingLotDTO.java
│   ├── ParkingSpotDTO.java
│   ├── ReservationDTO.java
│   ├── PaymentDTO.java
│   └── SubscriptionDTO.java
│
├── repository/                 # Data Access Layer
│   ├── UserRepository.java
│   ├── VehicleRepository.java
│   ├── ParkingLotRepository.java
│   ├── ParkingSpotRepository.java
│   ├── ReservationRepository.java
│   ├── PaymentRepository.java
│   └── SubscriptionRepository.java
│
├── service/                    # Business Logic Layer
│   ├── UserService.java
│   ├── VehicleService.java
│   ├── ParkingLotService.java
│   ├── ParkingSpotService.java
│   ├── ReservationService.java
│   ├── PaymentService.java
│   └── SubscriptionService.java
│
├── controller/                 # REST API Layer
│   ├── UserController.java
│   ├── VehicleController.java
│   ├── ParkingLotController.java
│   ├── ParkingSpotController.java
│   ├── ReservationController.java
│   ├── PaymentController.java
│   └── SubscriptionController.java
│
├── exception/                  # Exception Handling
│   ├── ResourceNotFoundException.java
│   ├── SpotNotAvailableException.java
│   ├── InvalidReservationTimeException.java
│   ├── PaymentFailedException.java
│   ├── DuplicateResourceException.java
│   └── GlobalExceptionHandler.java
│
└── DemoApplication.java       # Main Application Class
```

---

## 🔄 Data Flow

### Example: Creating a Reservation

```
1. Client Request
   POST /api/reservations
   {
     "userId": 1,
     "parkingSpotId": 1,
     "vehicleId": 1,
     "startTime": "2024-01-25T10:00:00",
     "endTime": "2024-01-25T12:00:00"
   }
   
2. Controller Layer (ReservationController)
   - Receives HTTP request
   - Validates @Valid annotations
   - Calls ReservationService
   
3. Service Layer (ReservationService)
   - Validates business rules:
     * User exists
     * Vehicle exists
     * Spot exists and is FREE
     * No time conflicts
     * Valid time range
   - Calculates price based on:
     * Duration
     * Vehicle type
     * Time of day
     * Day of week
     * Active subscription
   - Creates Reservation entity
   - Updates spot status to RESERVED
   - Saves to database
   
4. Repository Layer
   - Persists Reservation entity
   - Updates ParkingSpot entity
   - Returns saved entities
   
5. Service Layer
   - Converts entity to DTO
   - Returns ReservationDTO
   
6. Controller Layer
   - Wraps in ResponseEntity
   - Sets HTTP status to 201 (CREATED)
   - Returns JSON response

7. Client Response
   {
     "id": 1,
     "userId": 1,
     "parkingSpotId": 1,
     "vehicleId": 1,
     "startTime": "2024-01-25T10:00:00",
     "endTime": "2024-01-25T12:00:00",
     "price": 4.0,
     "status": "ACTIVE",
     "userName": "John Doe",
     "spotNumber": "A-101",
     "vehicleLicensePlate": "ABC-123"
   }
```

---

## 💾 Database Design

### Entity-Relationship Diagram (ERD)

```
┌──────────────┐
│     USER     │
│──────────────│
│ id (PK)      │──┐
│ name         │  │
│ email        │  │
│ password     │  │
│ role         │  │
└──────────────┘  │
                  │ 1:N
                  │
        ┌─────────┴──────────┐
        │                    │
┌───────▼────────┐  ┌───────▼──────────┐  ┌──────────────────┐
│    VEHICLE     │  │  SUBSCRIPTION    │  │   RESERVATION    │
│────────────────│  │──────────────────│  │──────────────────│
│ id (PK)        │  │ id (PK)          │  │ id (PK)          │
│ licensePlate   │  │ user_id (FK)     │  │ user_id (FK)     │
│ type           │  │ startDate        │  │ spot_id (FK)     │
│ user_id (FK)   │  │ endDate          │  │ vehicle_id (FK)  │
└────────┬───────┘  │ monthlyFee       │  │ startTime        │
         │          │ status           │  │ endTime          │
         │          └──────────────────┘  │ price            │
         │ 1:N                            │ status           │
         │                                └────────┬─────────┘
         │                                         │
         │                                         │ 1:1
         │                                         │
         │                                ┌────────▼─────────┐
         │                                │     PAYMENT      │
         │                                │──────────────────│
         │                                │ id (PK)          │
         │                                │ reservation_id(FK)│
         │                                │ amount           │
         │                                │ timestamp        │
         │                                │ method           │
         │                                │ status           │
         │                                └──────────────────┘
         │
┌────────▼──────────┐
│  PARKING_SPOT     │
│───────────────────│
│ id (PK)           │
│ spotNumber        │
│ status            │
│ parkingLot_id(FK) │
└────────┬──────────┘
         │ N:1
         │
┌────────▼──────────┐
│   PARKING_LOT     │
│───────────────────│
│ id (PK)           │
│ name              │
│ address           │
│ totalSpots        │
└───────────────────┘
```

### Database Tables

#### users
| Column   | Type         | Constraints                    |
|----------|--------------|--------------------------------|
| id       | BIGINT       | PRIMARY KEY, AUTO_INCREMENT    |
| name     | VARCHAR(100) | NOT NULL                       |
| email    | VARCHAR(255) | NOT NULL, UNIQUE               |
| password | VARCHAR(255) | NOT NULL                       |
| role     | VARCHAR(20)  | NOT NULL (CLIENT, ADMIN)       |

#### vehicles
| Column        | Type         | Constraints                 |
|---------------|--------------|----------------------------|
| id            | BIGINT       | PRIMARY KEY, AUTO_INCREMENT|
| license_plate | VARCHAR(50)  | NOT NULL, UNIQUE           |
| type          | VARCHAR(20)  | NOT NULL                   |
| user_id       | BIGINT       | FOREIGN KEY → users(id)    |

#### parking_lots
| Column      | Type         | Constraints                 |
|-------------|--------------|----------------------------|
| id          | BIGINT       | PRIMARY KEY, AUTO_INCREMENT|
| name        | VARCHAR(100) | NOT NULL                   |
| address     | VARCHAR(255) | NOT NULL                   |
| total_spots | INTEGER      | NOT NULL                   |

#### parking_spots
| Column         | Type        | Constraints                      |
|----------------|-------------|----------------------------------|
| id             | BIGINT      | PRIMARY KEY, AUTO_INCREMENT      |
| spot_number    | VARCHAR(50) | NOT NULL                         |
| status         | VARCHAR(20) | NOT NULL (FREE, OCCUPIED, etc.)  |
| parking_lot_id | BIGINT      | FOREIGN KEY → parking_lots(id)   |

#### reservations
| Column          | Type      | Constraints                      |
|-----------------|-----------|----------------------------------|
| id              | BIGINT    | PRIMARY KEY, AUTO_INCREMENT      |
| user_id         | BIGINT    | FOREIGN KEY → users(id)          |
| spot_id         | BIGINT    | FOREIGN KEY → parking_spots(id)  |
| vehicle_id      | BIGINT    | FOREIGN KEY → vehicles(id)       |
| start_time      | TIMESTAMP | NOT NULL                         |
| end_time        | TIMESTAMP | NOT NULL                         |
| price           | DOUBLE    | NOT NULL                         |
| status          | VARCHAR(20)| NOT NULL                        |

#### payments
| Column         | Type        | Constraints                      |
|----------------|-------------|----------------------------------|
| id             | BIGINT      | PRIMARY KEY, AUTO_INCREMENT      |
| reservation_id | BIGINT      | FOREIGN KEY → reservations(id)   |
| amount         | DOUBLE      | NOT NULL                         |
| timestamp      | TIMESTAMP   | NOT NULL                         |
| method         | VARCHAR(20) | NOT NULL                         |
| status         | VARCHAR(20) | NOT NULL                         |

#### subscriptions
| Column      | Type        | Constraints                 |
|-------------|-------------|----------------------------|
| id          | BIGINT      | PRIMARY KEY, AUTO_INCREMENT|
| user_id     | BIGINT      | FOREIGN KEY → users(id)    |
| start_date  | DATE        | NOT NULL                   |
| end_date    | DATE        | NOT NULL                   |
| monthly_fee | DOUBLE      | NOT NULL                   |
| status      | VARCHAR(20) | NOT NULL                   |

---

## 🎯 Design Patterns Used

### 1. Repository Pattern
- Abstracts data access logic
- Provides clean separation between business logic and data access
- Uses Spring Data JPA

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```

### 2. Service Layer Pattern
- Encapsulates business logic
- Provides transaction boundaries
- Coordinates between multiple repositories

```java
@Service
@Transactional
public class ReservationService {
    // Business logic here
}
```

### 3. DTO (Data Transfer Object) Pattern
- Separates internal domain models from external API contracts
- Provides clean API responses
- Reduces over-fetching

```java
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    // No password in response
}
```

### 4. Dependency Injection
- Uses Spring's IoC container
- Constructor injection with Lombok's @RequiredArgsConstructor
- Promotes loose coupling

```java
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
}
```

### 5. Strategy Pattern (Implicit)
- Price calculation varies based on multiple factors
- Different payment methods
- Different vehicle types

### 6. Builder Pattern (Lombok)
- Used implicitly through Lombok annotations
- Simplifies object creation

---

## 🔒 Security Considerations

### Current Implementation
- Input validation using Jakarta Validation
- SQL injection prevention through JPA/Hibernate
- Exception handling to prevent information leakage

### Future Enhancements (Not Implemented)
- Spring Security for authentication/authorization
- JWT tokens for stateless authentication
- Password encryption (BCrypt)
- Role-based access control (RBAC)
- API rate limiting

---

## ⚡ Performance Optimizations

### Current Implementations
1. **Lazy Loading**: Using `FetchType.LAZY` for relationships
2. **Database Indexing**: Primary keys, foreign keys, unique constraints
3. **Query Optimization**: Custom JPQL queries for specific use cases
4. **Transaction Management**: `@Transactional` for consistency

### Potential Improvements
1. **Caching**: Redis for frequently accessed data
2. **Connection Pooling**: HikariCP (already included in Spring Boot)
3. **Database Pagination**: For large result sets
4. **Asynchronous Processing**: For non-critical operations

---

## 🧪 Testing Strategy

### Unit Testing
- **Service Layer Tests**: Business logic validation
- **Controller Layer Tests**: API endpoint testing
- **Mocking**: Mockito for isolating dependencies

### Test Coverage
- Service tests: 22+ test cases
- Controller tests: 14+ test cases
- Total: 36+ test cases

---

## 📊 Business Logic Components

### 1. Price Calculation Engine
Location: `ReservationService.calculatePrice()`

```java
Price = BaseRate × Hours × Multipliers × Discounts

Multipliers:
- Night (22:00-06:00): ×0.8
- Weekend: ×1.2

Discounts:
- Electric Vehicle: ×0.9
- Active Subscription: ×0.7
```

### 2. Availability Checker
- Checks spot status
- Validates time conflicts
- Ensures no overlapping reservations

### 3. Payment Processor
- Validates payment amount
- Simulates payment gateway
- Updates payment status

### 4. Subscription Manager
- Tracks active subscriptions
- Applies discounts automatically
- Handles renewals and cancellations

---

## 🔄 API Design Principles

### RESTful Design
- Resource-based URLs
- HTTP methods (GET, POST, PUT, PATCH, DELETE)
- Proper status codes
- JSON responses

### URL Structure
```
/api/{resource}              # Collection
/api/{resource}/{id}         # Individual resource
/api/{resource}/search       # Search endpoint
/api/{resource}/{id}/action  # Resource action
```

### HTTP Status Codes
- `200 OK` - Successful GET, PUT, PATCH
- `201 Created` - Successful POST
- `204 No Content` - Successful DELETE
- `400 Bad Request` - Validation errors
- `404 Not Found` - Resource not found
- `409 Conflict` - Business rule violation
- `500 Internal Server Error` - Server errors

---

## 🌟 Best Practices Implemented

1. **Clean Code**
   - Meaningful variable names
   - Single Responsibility Principle
   - DRY (Don't Repeat Yourself)

2. **SOLID Principles**
   - Single Responsibility
   - Dependency Inversion
   - Interface Segregation

3. **Exception Handling**
   - Custom exceptions
   - Global exception handler
   - Consistent error responses

4. **Validation**
   - Input validation
   - Business rule validation
   - Database constraints

5. **Documentation**
   - Comprehensive README
   - API examples
   - Architecture documentation

---

## 📈 Scalability Considerations

### Current Architecture Supports
- Horizontal scaling (multiple instances)
- Database optimization
- Stateless design

### Future Scaling Options
1. **Microservices**: Split into smaller services
2. **Load Balancing**: Distribute traffic
3. **Caching Layer**: Redis/Memcached
4. **Message Queue**: RabbitMQ/Kafka for async operations
5. **CDN**: For static content

---

## 🔧 Configuration Management

### Application Profiles
- **Default (H2)**: Development and testing
- **PostgreSQL**: Production environment

### Environment-Specific Configuration
- `application.properties` - Default (H2)
- `application-postgres.properties` - PostgreSQL profile

---

## 📝 Logging Strategy

### Log Levels
- **DEBUG**: SQL queries, method entries
- **INFO**: Application lifecycle, important events
- **WARN**: Deprecated features, recoverable issues
- **ERROR**: Exceptions, critical failures

### Logging Configuration
```properties
logging.level.com.example.demo=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

---

## 🎓 Learning Outcomes

This project demonstrates:
1. Spring Boot application development
2. RESTful API design
3. JPA/Hibernate ORM
4. Business logic implementation
5. Testing with JUnit and Mockito
6. Exception handling
7. DTO pattern and mapping
8. Database relationships
9. Transaction management
10. Clean architecture principles

---

## 📚 References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate ORM](https://hibernate.org/)
- [RESTful API Design Best Practices](https://restfulapi.net/)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
