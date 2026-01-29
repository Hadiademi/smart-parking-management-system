# Smart Parking System - API Testing Examples

This document provides complete examples for testing all API endpoints using tools like Postman, cURL, or any REST client.

---

## 🔧 Getting Started

**Base URL:** `http://localhost:8080/api`

All requests use `Content-Type: application/json`

---

## 1️⃣ User Management

### Create User
```bash
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "name": "Arber Kosova",
  "email": "arber@example.com",
  "password": "password123",
  "role": "CLIENT"
}
```

### Create Admin User
```bash
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "name": "Admin User",
  "email": "admin@smartparking.com",
  "password": "admin123",
  "role": "ADMIN"
}
```

### Get All Users
```bash
GET http://localhost:8080/api/users
```

### Get User by ID
```bash
GET http://localhost:8080/api/users/1
```

### Search Users by Name
```bash
GET http://localhost:8080/api/users/search?keyword=Arber
```

### Get User by Email
```bash
GET http://localhost:8080/api/users/email/arber@example.com
```

### Update User
```bash
PUT http://localhost:8080/api/users/1
Content-Type: application/json

{
  "name": "Arber Kosova Updated",
  "email": "arber.updated@example.com",
  "password": "newpassword123",
  "role": "CLIENT"
}
```

### Delete User
```bash
DELETE http://localhost:8080/api/users/1
```

---

## 2️⃣ Vehicle Management

### Create Vehicle
```bash
POST http://localhost:8080/api/vehicles
Content-Type: application/json

{
  "licensePlate": "PR-1234-AA",
  "type": "CAR",
  "userId": 1
}
```

### Create Electric Vehicle
```bash
POST http://localhost:8080/api/vehicles
Content-Type: application/json

{
  "licensePlate": "PR-5678-BB",
  "type": "ELECTRIC",
  "userId": 1
}
```

### Create Motorcycle
```bash
POST http://localhost:8080/api/vehicles
Content-Type: application/json

{
  "licensePlate": "PR-9012-CC",
  "type": "MOTORCYCLE",
  "userId": 1
}
```

### Get All Vehicles
```bash
GET http://localhost:8080/api/vehicles
```

### Get Vehicle by ID
```bash
GET http://localhost:8080/api/vehicles/1
```

### Get Vehicles by User
```bash
GET http://localhost:8080/api/vehicles/user/1
```

### Update Vehicle
```bash
PUT http://localhost:8080/api/vehicles/1
Content-Type: application/json

{
  "licensePlate": "PR-1234-AA",
  "type": "ELECTRIC",
  "userId": 1
}
```

### Delete Vehicle
```bash
DELETE http://localhost:8080/api/vehicles/1
```

---

## 3️⃣ Parking Lot Management

### Create Parking Lot
```bash
POST http://localhost:8080/api/parking-lots
Content-Type: application/json

{
  "name": "Parking Qendra",
  "address": "Rruga Dëshmorët e Kombit, Prishtinë",
  "totalSpots": 200
}
```

### Create Another Parking Lot
```bash
POST http://localhost:8080/api/parking-lots
Content-Type: application/json

{
  "name": "Parking Grand",
  "address": "Rr. UÇK, Prishtinë",
  "totalSpots": 150
}
```

### Get All Parking Lots
```bash
GET http://localhost:8080/api/parking-lots
```

### Get Parking Lot by ID
```bash
GET http://localhost:8080/api/parking-lots/1
```

### Search by Name
```bash
GET http://localhost:8080/api/parking-lots/search/name?name=Qendra
```

### Search by Address
```bash
GET http://localhost:8080/api/parking-lots/search/address?address=Prishtinë
```

### Update Parking Lot
```bash
PUT http://localhost:8080/api/parking-lots/1
Content-Type: application/json

{
  "name": "Parking Qendra (Updated)",
  "address": "Rruga Dëshmorët e Kombit 123, Prishtinë",
  "totalSpots": 250
}
```

### Delete Parking Lot
```bash
DELETE http://localhost:8080/api/parking-lots/1
```

---

## 4️⃣ Parking Spot Management

### Create Parking Spots
```bash
POST http://localhost:8080/api/parking-spots
Content-Type: application/json

{
  "spotNumber": "A-101",
  "status": "FREE",
  "parkingLotId": 1
}
```

```bash
POST http://localhost:8080/api/parking-spots
Content-Type: application/json

{
  "spotNumber": "A-102",
  "status": "FREE",
  "parkingLotId": 1
}
```

```bash
POST http://localhost:8080/api/parking-spots
Content-Type: application/json

{
  "spotNumber": "B-201",
  "status": "FREE",
  "parkingLotId": 1
}
```

### Get All Parking Spots
```bash
GET http://localhost:8080/api/parking-spots
```

### Get Available Spots Only
```bash
GET http://localhost:8080/api/parking-spots/available
```

### Get Spots by Parking Lot
```bash
GET http://localhost:8080/api/parking-spots/parking-lot/1
```

### Get Available Spots in Specific Parking Lot
```bash
GET http://localhost:8080/api/parking-spots/parking-lot/1/available
```

### Update Spot Status
```bash
PATCH http://localhost:8080/api/parking-spots/1/status?status=MAINTENANCE
```

### Delete Parking Spot
```bash
DELETE http://localhost:8080/api/parking-spots/1
```

---

## 5️⃣ Reservation Management

### Create Reservation (Basic - 2 hours, normal car)
```bash
POST http://localhost:8080/api/reservations
Content-Type: application/json

{
  "userId": 1,
  "parkingSpotId": 1,
  "vehicleId": 1,
  "startTime": "2024-01-25T10:00:00",
  "endTime": "2024-01-25T12:00:00"
}
```
**Expected Price:** €4.00 (2 hours × €2.00)

### Create Reservation (Electric Vehicle)
```bash
POST http://localhost:8080/api/reservations
Content-Type: application/json

{
  "userId": 1,
  "parkingSpotId": 2,
  "vehicleId": 2,
  "startTime": "2024-01-25T10:00:00",
  "endTime": "2024-01-25T12:00:00"
}
```
**Expected Price:** €3.60 (2 hours × €2.00 × 0.9)

### Create Reservation (Night Time)
```bash
POST http://localhost:8080/api/reservations
Content-Type: application/json

{
  "userId": 1,
  "parkingSpotId": 3,
  "vehicleId": 1,
  "startTime": "2024-01-25T23:00:00",
  "endTime": "2024-01-26T01:00:00"
}
```
**Expected Price:** €3.20 (2 hours × €2.00 × 0.8)

### Create Reservation (Weekend)
```bash
POST http://localhost:8080/api/reservations
Content-Type: application/json

{
  "userId": 1,
  "parkingSpotId": 1,
  "vehicleId": 1,
  "startTime": "2024-01-27T10:00:00",
  "endTime": "2024-01-27T12:00:00"
}
```
**Expected Price:** €4.80 (2 hours × €2.00 × 1.2) - If Jan 27 is Saturday

### Get All Reservations
```bash
GET http://localhost:8080/api/reservations
```

### Get Reservation by ID
```bash
GET http://localhost:8080/api/reservations/1
```

### Get Reservations by User
```bash
GET http://localhost:8080/api/reservations/user/1
```

### Get Reservations by Status
```bash
GET http://localhost:8080/api/reservations/status/ACTIVE
```

### Cancel Reservation
```bash
PATCH http://localhost:8080/api/reservations/1/cancel
```

### Cleanup Expired Reservations
```bash
POST http://localhost:8080/api/reservations/cleanup-expired
```

---

## 6️⃣ Payment Management

### Create Payment
```bash
POST http://localhost:8080/api/payments
Content-Type: application/json

{
  "amount": 4.0,
  "method": "CREDIT_CARD",
  "reservationId": 1
}
```

### Create Payment (Different Methods)
```bash
POST http://localhost:8080/api/payments
Content-Type: application/json

{
  "amount": 3.60,
  "method": "PAYPAL",
  "reservationId": 2
}
```

```bash
POST http://localhost:8080/api/payments
Content-Type: application/json

{
  "amount": 3.20,
  "method": "CASH",
  "reservationId": 3
}
```

### Get All Payments
```bash
GET http://localhost:8080/api/payments
```

### Get Payment by ID
```bash
GET http://localhost:8080/api/payments/1
```

### Get Payment by Reservation
```bash
GET http://localhost:8080/api/payments/reservation/1
```

### Get Payments by Status
```bash
GET http://localhost:8080/api/payments/status/COMPLETED
```

### Get Payments by Date Range
```bash
GET http://localhost:8080/api/payments/date-range?startDate=2024-01-01T00:00:00&endDate=2024-01-31T23:59:59
```

### Get Total Revenue
```bash
GET http://localhost:8080/api/payments/revenue/total
```

### Refund Payment
```bash
PATCH http://localhost:8080/api/payments/1/refund
```

---

## 7️⃣ Subscription Management

### Create Subscription (Monthly)
```bash
POST http://localhost:8080/api/subscriptions
Content-Type: application/json

{
  "userId": 1,
  "startDate": "2024-01-01",
  "endDate": "2024-01-31",
  "monthlyFee": 50.0
}
```

### Create Subscription (3 Months)
```bash
POST http://localhost:8080/api/subscriptions
Content-Type: application/json

{
  "userId": 2,
  "startDate": "2024-01-01",
  "endDate": "2024-03-31",
  "monthlyFee": 45.0
}
```

### Get All Subscriptions
```bash
GET http://localhost:8080/api/subscriptions
```

### Get Subscription by ID
```bash
GET http://localhost:8080/api/subscriptions/1
```

### Get Subscriptions by User
```bash
GET http://localhost:8080/api/subscriptions/user/1
```

### Get Active Subscription for User
```bash
GET http://localhost:8080/api/subscriptions/user/1/active
```

### Renew Subscription
```bash
PATCH http://localhost:8080/api/subscriptions/1/renew
```

### Cancel Subscription
```bash
PATCH http://localhost:8080/api/subscriptions/1/cancel
```

### Expire Old Subscriptions
```bash
POST http://localhost:8080/api/subscriptions/expire-old
```

### Delete Subscription
```bash
DELETE http://localhost:8080/api/subscriptions/1
```

---

## 🧪 Complete Test Flow

### Step 1: Create User
```bash
POST http://localhost:8080/api/users
{
  "name": "Test User",
  "email": "test@example.com",
  "password": "test123",
  "role": "CLIENT"
}
```
Response: `userId = 1`

### Step 2: Create Vehicle
```bash
POST http://localhost:8080/api/vehicles
{
  "licensePlate": "TEST-123",
  "type": "CAR",
  "userId": 1
}
```
Response: `vehicleId = 1`

### Step 3: Create Parking Lot
```bash
POST http://localhost:8080/api/parking-lots
{
  "name": "Test Parking",
  "address": "Test Address",
  "totalSpots": 100
}
```
Response: `parkingLotId = 1`

### Step 4: Create Parking Spot
```bash
POST http://localhost:8080/api/parking-spots
{
  "spotNumber": "A-001",
  "status": "FREE",
  "parkingLotId": 1
}
```
Response: `spotId = 1`

### Step 5: Create Subscription (Optional)
```bash
POST http://localhost:8080/api/subscriptions
{
  "userId": 1,
  "startDate": "2024-01-01",
  "endDate": "2024-01-31",
  "monthlyFee": 50.0
}
```

### Step 6: Create Reservation
```bash
POST http://localhost:8080/api/reservations
{
  "userId": 1,
  "parkingSpotId": 1,
  "vehicleId": 1,
  "startTime": "2024-01-25T10:00:00",
  "endTime": "2024-01-25T12:00:00"
}
```
Response: `reservationId = 1, price = 2.80` (with subscription)

### Step 7: Create Payment
```bash
POST http://localhost:8080/api/payments
{
  "amount": 2.80,
  "method": "CREDIT_CARD",
  "reservationId": 1
}
```

### Step 8: Check Results
```bash
GET http://localhost:8080/api/reservations/1
GET http://localhost:8080/api/payments/1
GET http://localhost:8080/api/payments/revenue/total
```

---

## ⚠️ Error Examples

### User Already Exists
```bash
POST http://localhost:8080/api/users
{
  "name": "Test",
  "email": "test@example.com",
  "password": "test123"
}
```
Response:
```json
{
  "timestamp": "2024-01-20T10:30:00",
  "status": 409,
  "error": "Duplicate Resource",
  "message": "Email already exists: test@example.com",
  "path": "/api/users"
}
```

### Spot Not Available
```bash
POST http://localhost:8080/api/reservations
{
  "userId": 1,
  "parkingSpotId": 1,
  "vehicleId": 1,
  "startTime": "2024-01-25T10:00:00",
  "endTime": "2024-01-25T12:00:00"
}
```
Response (if spot already reserved):
```json
{
  "timestamp": "2024-01-20T10:30:00",
  "status": 409,
  "error": "Spot Not Available",
  "message": "Spot is already reserved for the selected time period",
  "path": "/api/reservations"
}
```

### Invalid Reservation Time
```bash
POST http://localhost:8080/api/reservations
{
  "userId": 1,
  "parkingSpotId": 1,
  "vehicleId": 1,
  "startTime": "2024-01-20T10:00:00",
  "endTime": "2024-01-20T09:00:00"
}
```
Response:
```json
{
  "timestamp": "2024-01-20T10:30:00",
  "status": 400,
  "error": "Invalid Reservation Time",
  "message": "End time must be after start time",
  "path": "/api/reservations"
}
```

---

## 📝 Notes

- Replace date/time values with future dates when testing
- IDs may vary depending on the order of creation
- Payment amounts must match reservation prices
- All endpoints return proper HTTP status codes (200, 201, 400, 404, 409, etc.)
- Use H2 Console at `http://localhost:8080/h2-console` to inspect database

---

## 🎯 Testing Tips

1. **Create in Order**: User → Vehicle → ParkingLot → ParkingSpot → Reservation → Payment
2. **Check Availability**: Use `/parking-spots/available` before creating reservations
3. **Test Pricing**: Try different vehicle types, times, and with/without subscriptions
4. **Test Validations**: Try invalid data to see error responses
5. **Test Business Logic**: Try overlapping reservations, past dates, etc.
