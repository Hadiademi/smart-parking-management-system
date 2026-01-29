# Smart Parking Management System

## 📋 Project Description

Smart Parking Management System është një aplikacion backend i zhvilluar me Spring Boot, i cili menaxhon procesin e parkimit në mënyrë inteligjente për ambiente parkimi në qytet. Sistemi mundëson administrimin e përdoruesve, automjeteve, vendparkimeve, rezervimeve dhe pagesave.

### Karakteristikat Kryesore

- ✅ **Menaxhimi i Përdoruesve dhe Automjeteve**: CRUD operations për users dhe vehicles
- ✅ **Menaxhimi i Parking Lots dhe Spots**: Administrimi i vendparkimeve dhe statusit të tyre
- ✅ **Sistemë Inteligjente Rezervimesh**: Kontrolli i disponueshmërisë dhe parandalimi i konflikteve
- ✅ **Llogaritje Automatike e Çmimit**: Bazuar në kohëzgjatje, lloj automjeti, kohë ditës, dhe abonim
- ✅ **Sistemë Pagesash**: Procesimi i pagesave me statuse të ndryshme
- ✅ **Abonime Mujore**: Zbritje speciale për përdoruesit e rregullt
- ✅ **Validime të Plota**: Input validation, business rules, dhe exception handling
- ✅ **RESTful API**: Endpoints të strukturuar dhe të dokumentuar mirë
- ✅ **Unit Testing**: Testime të hollësishme me JUnit dhe Mockito

---

## 🛠️ Technologies Used

### Backend Framework
- **Spring Boot 4.0.2** - Main application framework
- **Spring Web** - RESTful API development
- **Spring Data JPA** - Database access and ORM
- **Spring Validation** - Input validation

### Database
- **H2 Database** - In-memory database (default for development)
- **PostgreSQL** - Production database support

### Tools & Libraries
- **Lombok** - Reducing boilerplate code
- **ModelMapper** - DTO mapping
- **Hibernate** - ORM framework
- **Gradle** - Build automation

### Testing
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework
- **Spring Boot Test** - Integration testing support

### Other
- **Java 17** - Programming language
- **SLF4J** - Logging

---

## 📐 System Architecture

### Layered Architecture

```
┌─────────────────────────────────────────┐
│         REST Controllers Layer          │
│    (UserController, ReservationController, etc.) │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│          Service Layer                  │
│  (Business Logic, Calculations, Validations) │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│        Repository Layer                 │
│      (Data Access with JPA)             │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│          Database Layer                 │
│         (H2 / PostgreSQL)               │
└─────────────────────────────────────────┘
```

### Package Structure

```
com.example.demo
├── entity/          # Domain models (User, Vehicle, Reservation, etc.)
├── repository/      # JPA Repositories
├── service/         # Business logic
├── controller/      # REST API endpoints
├── dto/            # Data Transfer Objects
├── exception/      # Custom exceptions and global handler
└── DemoApplication.java
```

---

## 💾 Database Schema

### Entities and Relationships

#### 7 Core Entities:

1. **User** - Informacioni i përdoruesve (klient/admin)
2. **Vehicle** - Automjetet që i përkasin përdoruesve
3. **ParkingLot** - Njësi parkimi me lokacion dhe kapacitet
4. **ParkingSpot** - Vende individuale parkimi
5. **Reservation** - Rezervimet e vendparkimeve
6. **Payment** - Pagesa për rezervime
7. **Subscription** - Abonime mujore për përdoruesit

#### Relationships:

- **User ↔ Vehicle**: One-to-Many (Një user mund të ketë shumë automjete)
- **User ↔ Reservation**: One-to-Many (Një user mund të krijojë shumë rezervime)
- **User ↔ Subscription**: One-to-Many (Një user mund të ketë shumë abonime)
- **ParkingLot ↔ ParkingSpot**: One-to-Many (Një parking ka shumë vende)
- **ParkingSpot ↔ Reservation**: One-to-Many (Një vend mund të rezervohet në kohë të ndryshme)
- **Vehicle ↔ Reservation**: One-to-Many (Një automjet mund të përdoret në shumë rezervime)
- **Reservation ↔ Payment**: One-to-One (Çdo rezervim ka një pagesë)

---

## 🚀 How to Run the Project

### Prerequisites

- Java 17 or higher
- Gradle (or use the included Gradle wrapper)
- PostgreSQL (optional, if using PostgreSQL instead of H2)

### Option 1: Running with H2 Database (Default)

1. **Clone the repository**
```bash
git clone <your-repo-url>
cd demo
```

2. **Build the project**
```bash
./gradlew build
```

3. **Run the application**
```bash
./gradlew bootRun
```

4. **Access the application**
- Application: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:smartparkingdb`
  - Username: `sa`
  - Password: (leave empty)

### Option 2: Running with PostgreSQL

1. **Create PostgreSQL database**
```sql
CREATE DATABASE smartparkingdb;
```

2. **Update application.properties**

Uncomment PostgreSQL configuration in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/smartparkingdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Comment out H2 configuration.

3. **Run the application**
```bash
./gradlew bootRun
```

### Option 3: Using PostgreSQL Profile

```bash
./gradlew bootRun --args='--spring.profiles.active=postgres'
```

### Running Tests

```bash
./gradlew test
```

---

## 📡 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Users API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users` | Create new user |
| GET | `/users` | Get all users |
| GET | `/users/{id}` | Get user by ID |
| GET | `/users/email/{email}` | Get user by email |
| GET | `/users/search?keyword={keyword}` | Search users by name |
| PUT | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Delete user |

**Example Request - Create User:**
```json
POST /api/users
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "CLIENT"
}
```

### Vehicles API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/vehicles` | Create new vehicle |
| GET | `/vehicles` | Get all vehicles |
| GET | `/vehicles/{id}` | Get vehicle by ID |
| GET | `/vehicles/user/{userId}` | Get vehicles by user |
| PUT | `/vehicles/{id}` | Update vehicle |
| DELETE | `/vehicles/{id}` | Delete vehicle |

**Example Request - Create Vehicle:**
```json
POST /api/vehicles
{
  "licensePlate": "ABC-123",
  "type": "CAR",
  "userId": 1
}
```

### Parking Lots API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/parking-lots` | Create parking lot |
| GET | `/parking-lots` | Get all parking lots |
| GET | `/parking-lots/{id}` | Get parking lot by ID |
| GET | `/parking-lots/search/name?name={name}` | Search by name |
| GET | `/parking-lots/search/address?address={addr}` | Search by address |
| PUT | `/parking-lots/{id}` | Update parking lot |
| DELETE | `/parking-lots/{id}` | Delete parking lot |

**Example Request - Create Parking Lot:**
```json
POST /api/parking-lots
{
  "name": "City Center Parking",
  "address": "Rruga Dëshmorët e Kombit, Prishtinë",
  "totalSpots": 200
}
```

### Parking Spots API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/parking-spots` | Create parking spot |
| GET | `/parking-spots` | Get all spots |
| GET | `/parking-spots/{id}` | Get spot by ID |
| GET | `/parking-spots/available` | Get all available spots |
| GET | `/parking-spots/parking-lot/{id}` | Get spots by parking lot |
| GET | `/parking-spots/parking-lot/{id}/available` | Get available spots in parking lot |
| PATCH | `/parking-spots/{id}/status?status={status}` | Update spot status |
| DELETE | `/parking-spots/{id}` | Delete spot |

**Example Request - Create Parking Spot:**
```json
POST /api/parking-spots
{
  "spotNumber": "A-101",
  "status": "FREE",
  "parkingLotId": 1
}
```

### Reservations API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/reservations` | Create reservation |
| GET | `/reservations` | Get all reservations |
| GET | `/reservations/{id}` | Get reservation by ID |
| GET | `/reservations/user/{userId}` | Get user's reservations |
| GET | `/reservations/status/{status}` | Get reservations by status |
| PATCH | `/reservations/{id}/cancel` | Cancel reservation |
| POST | `/reservations/cleanup-expired` | Cleanup expired reservations |

**Example Request - Create Reservation:**
```json
POST /api/reservations
{
  "userId": 1,
  "parkingSpotId": 1,
  "vehicleId": 1,
  "startTime": "2024-01-20T10:00:00",
  "endTime": "2024-01-20T12:00:00"
}
```

**Response:**
```json
{
  "id": 1,
  "userId": 1,
  "parkingSpotId": 1,
  "vehicleId": 1,
  "startTime": "2024-01-20T10:00:00",
  "endTime": "2024-01-20T12:00:00",
  "price": 4.0,
  "status": "ACTIVE",
  "userName": "John Doe",
  "spotNumber": "A-101",
  "vehicleLicensePlate": "ABC-123"
}
```

### Payments API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/payments` | Create payment |
| GET | `/payments` | Get all payments |
| GET | `/payments/{id}` | Get payment by ID |
| GET | `/payments/reservation/{reservationId}` | Get payment by reservation |
| GET | `/payments/status/{status}` | Get payments by status |
| GET | `/payments/date-range?startDate={start}&endDate={end}` | Get payments in date range |
| GET | `/payments/revenue/total` | Get total revenue |
| PATCH | `/payments/{id}/refund` | Refund payment |

**Example Request - Create Payment:**
```json
POST /api/payments
{
  "amount": 4.0,
  "method": "CREDIT_CARD",
  "reservationId": 1
}
```

### Subscriptions API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/subscriptions` | Create subscription |
| GET | `/subscriptions` | Get all subscriptions |
| GET | `/subscriptions/{id}` | Get subscription by ID |
| GET | `/subscriptions/user/{userId}` | Get user's subscriptions |
| GET | `/subscriptions/user/{userId}/active` | Get active subscription |
| PATCH | `/subscriptions/{id}/renew` | Renew subscription |
| PATCH | `/subscriptions/{id}/cancel` | Cancel subscription |
| POST | `/subscriptions/expire-old` | Expire old subscriptions |
| DELETE | `/subscriptions/{id}` | Delete subscription |

**Example Request - Create Subscription:**
```json
POST /api/subscriptions
{
  "userId": 1,
  "startDate": "2024-01-01",
  "endDate": "2024-01-31",
  "monthlyFee": 50.0
}
```

---

## 💰 Pricing Logic

### Base Rate
- **€2.00 per hour** - Standard rate

### Multipliers & Discounts

1. **Night Discount (22:00 - 06:00)**: -20% (×0.8)
2. **Weekend Surcharge (Sat-Sun)**: +20% (×1.2)
3. **Electric Vehicle**: -10% (×0.9)
4. **Active Subscription**: -30% (×0.7)

### Example Calculations

**Scenario 1:** 2 hours, normal car, Tuesday 10:00 AM, no subscription
```
Price = 2 hours × €2.00 = €4.00
```

**Scenario 2:** 2 hours, electric car, Tuesday 10:00 AM, no subscription
```
Price = 2 hours × €2.00 × 0.9 (electric) = €3.60
```

**Scenario 3:** 2 hours, normal car, Tuesday 10:00 AM, with subscription
```
Price = 2 hours × €2.00 × 0.7 (subscription) = €2.80
```

**Scenario 4:** 2 hours, normal car, Tuesday 23:00 (night), with subscription
```
Price = 2 hours × €2.00 × 0.8 (night) × 0.7 (subscription) = €2.24
```

---

## 🔒 Validations

### User
- Name: 2-100 characters, required
- Email: Valid email format, unique, required
- Password: Minimum 6 characters, required

### Vehicle
- License Plate: Only uppercase letters, numbers, and hyphens, unique, required
- Type: Must be one of: CAR, MOTORCYCLE, TRUCK, VAN, ELECTRIC

### Reservation
- Start Time: Cannot be in the past
- End Time: Must be after start time
- Duration: Minimum 15 minutes, maximum 24 hours
- Spot: Must be available and not have conflicting reservations

### Payment
- Amount: Must be greater than 0
- Amount: Must match reservation price

### Subscription
- End Date: Must be after start date
- Monthly Fee: Must be greater than 0

---

## 🧪 Testing

### Test Coverage

The project includes comprehensive unit tests:

#### Service Tests (2 minimum)
1. **ReservationServiceTest** - 11 test cases
   - Reservation creation with validations
   - Price calculation with various scenarios
   - Cancellation logic
   - Exception handling

2. **PaymentServiceTest** - 11 test cases
   - Payment processing
   - Validation checks
   - Refund logic
   - Revenue calculations

#### Controller Tests (2 minimum)
1. **UserControllerTest** - 7 test cases
   - CRUD operations
   - Search functionality
   - Response validation

2. **ReservationControllerTest** - 7 test cases
   - API endpoint testing
   - Request/response validation
   - Status code verification

### Running Tests
```bash
./gradlew test
```

### Test Report
After running tests, view the report at:
```
build/reports/tests/test/index.html
```

---

## 🛡️ Exception Handling

### Custom Exceptions

- **ResourceNotFoundException** - When entity is not found
- **SpotNotAvailableException** - When parking spot is occupied
- **InvalidReservationTimeException** - When reservation times are invalid
- **PaymentFailedException** - When payment processing fails
- **DuplicateResourceException** - When unique constraint is violated

### Global Exception Handler

All exceptions are handled globally with consistent error responses:

```json
{
  "timestamp": "2024-01-20T10:30:00",
  "status": 404,
  "error": "Resource Not Found",
  "message": "User not found with id: 999",
  "path": "/api/users/999"
}
```

---

## 📊 Business Rules

1. **Spot Availability**: A spot can only be reserved if it's FREE
2. **No Overlapping Reservations**: System checks for conflicting time slots
3. **Time Validation**: Start time must be in the future, end after start
4. **Payment Verification**: Payment amount must match reservation price
5. **Subscription Benefits**: Active subscribers get 30% discount
6. **Electric Vehicle Incentive**: 10% discount for electric vehicles
7. **Automatic Cleanup**: Expired reservations automatically free spots

---

## 🎯 Project Highlights

### Requirements Met

✅ **7 Entities** - User, Vehicle, ParkingLot, ParkingSpot, Reservation, Payment, Subscription  
✅ **Clear Relationships** - All entities properly connected  
✅ **CRUD Operations** - Full CRUD for all entities  
✅ **Business Logic** - Price calculation, availability checking, validations  
✅ **Filtering & Searching** - By status, user, date range, name, etc.  
✅ **Sorting** - Available in repository queries  
✅ **DTO Mapping** - ModelMapper for clean separation  
✅ **Error Handling** - Custom exceptions with global handler  
✅ **Validations** - Jakarta Validation annotations  
✅ **Unit Tests** - 4+ tests (11 + 11 + 7 + 7 = 36 total)  
✅ **RESTful API** - Clean, well-structured endpoints  
✅ **Documentation** - Comprehensive README  

---

## 👨‍💻 Author

Created as a final project for Spring Boot course.

---

## 📝 License

This project is created for educational purposes.

---

## 🔗 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2 Database](https://www.h2database.com/)
- [PostgreSQL](https://www.postgresql.org/)

---

## 📞 Support

For questions or issues, please create an issue in the repository.
