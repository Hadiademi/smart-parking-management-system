# 🧪 KOMANDA PËR TESTING - Smart Parking System

## 📋 PJESA 1: TESTE NË BROWSER (MË TË THJESHTAT)

Hap Chrome/Firefox dhe shkruaj këto URL. Duhet të shohësh JSON responses.

---

### ✅ **1. Shiko të gjithë Users:**
```
http://localhost:9090/api/users
```
**Duhet të shohësh:** 4 users (Admin, Arber, Liridona, Arta)

---

### ✅ **2. Shiko një User specifik:**
```
http://localhost:9090/api/users/1
```
**Duhet të shohësh:** Admin User details

---

### ✅ **3. Shiko të gjitha Vehicles:**
```
http://localhost:9090/api/vehicles
```
**Duhet të shohësh:** 4 vehicles (2 për Arber, 1 për Liridona, 1 për Arta)

---

### ✅ **4. Shiko Vehicles të një User:**
```
http://localhost:9090/api/vehicles/user/1
```
**Duhet të shohësh:** Vehicles të user me ID 1

---

### ✅ **5. Shiko të gjithë Parking Lots:**
```
http://localhost:9090/api/parking-lots
```
**Duhet të shohësh:** 3 parking lots (Qendra, Grand, Prishtina Mall)

---

### ✅ **6. Shiko të gjitha Parking Spots:**
```
http://localhost:9090/api/parking-spots
```
**Duhet të shohësh:** 10 parking spots

---

### ✅ **7. Shiko vetëm Spots e lira (Available):**
```
http://localhost:9090/api/parking-spots/available
```
**Duhet të shohësh:** Spots me status = FREE

---

### ✅ **8. Shiko Spots për një Parking Lot specifik:**
```
http://localhost:9090/api/parking-spots/parking-lot/1
```
**Duhet të shohësh:** Spots në Parking Lot me ID 1

---

### ✅ **9. Shiko Subscriptions:**
```
http://localhost:9090/api/subscriptions
```
**Duhet të shohësh:** 2 subscriptions (1 active, 1 expired)

---

### ✅ **10. Shiko Active Subscription për një User:**
```
http://localhost:9090/api/subscriptions/user/1/active
```
**Duhet të shohësh:** Active subscription për user me ID 1

---

## 📋 PJESA 2: TESTE ME POSTMAN (PËR POST/PUT/DELETE)

### Shkarkoni Postman:
https://www.postman.com/downloads/

---

## 🚗 **TEST 1: Krijo një User të Ri**

### Request:
- **Method:** POST
- **URL:** `http://localhost:9090/api/users`
- **Headers:** `Content-Type: application/json`
- **Body (raw JSON):**
```json
{
  "name": "Test User",
  "email": "test@example.com",
  "password": "password123",
  "role": "CLIENT"
}
```

### Expected Response:
```json
{
  "id": 5,
  "name": "Test User",
  "email": "test@example.com",
  "role": "CLIENT"
}
```

---

## 🚗 **TEST 2: Krijo një Vehicle**

### Request:
- **Method:** POST
- **URL:** `http://localhost:9090/api/vehicles`
- **Body:**
```json
{
  "licensePlate": "TEST-999",
  "type": "CAR",
  "userId": 1
}
```

### Expected Response:
```json
{
  "id": 5,
  "licensePlate": "TEST-999",
  "type": "CAR",
  "userId": 1,
  "userName": "Admin User"
}
```

---

## 🅿️ **TEST 3: Krijo një Parking Lot**

### Request:
- **Method:** POST
- **URL:** `http://localhost:9090/api/parking-lots`
- **Body:**
```json
{
  "name": "Test Parking",
  "address": "Test Address 123",
  "totalSpots": 50
}
```

---

## 🅿️ **TEST 4: Krijo një Parking Spot**

### Request:
- **Method:** POST
- **URL:** `http://localhost:9090/api/parking-spots`
- **Body:**
```json
{
  "spotNumber": "TEST-001",
  "status": "FREE",
  "parkingLotId": 1
}
```

---

## 📅 **TEST 5: Krijo një Rezervim (MË E RËNDËSISHMJA!)**

### Request:
- **Method:** POST
- **URL:** `http://localhost:9090/api/reservations`
- **Body:**
```json
{
  "userId": 1,
  "parkingSpotId": 1,
  "vehicleId": 1,
  "startTime": "2024-02-01T10:00:00",
  "endTime": "2024-02-01T12:00:00"
}
```

### Expected Response:
```json
{
  "id": 1,
  "userId": 1,
  "parkingSpotId": 1,
  "vehicleId": 1,
  "startTime": "2024-02-01T10:00:00",
  "endTime": "2024-02-01T12:00:00",
  "price": 2.8,
  "status": "ACTIVE",
  "userName": "Admin User",
  "spotNumber": "A-101",
  "vehicleLicensePlate": "PR-1234-AA"
}
```

**VËREJ:** Çmimi = 2.8€ (2 hours × 2€ × 0.7 subscription discount)

---

## 💳 **TEST 6: Krijo një Payment**

### Request:
- **Method:** POST
- **URL:** `http://localhost:9090/api/payments`
- **Body:**
```json
{
  "amount": 2.8,
  "method": "CREDIT_CARD",
  "reservationId": 1
}
```

### Expected Response:
```json
{
  "id": 1,
  "amount": 2.8,
  "method": "CREDIT_CARD",
  "status": "COMPLETED",
  "reservationId": 1,
  "timestamp": "2024-01-29T19:30:00"
}
```

---

## 📊 **TEST 7: Shiko Total Revenue**

### Request:
- **Method:** GET
- **URL:** `http://localhost:9090/api/payments/revenue/total`

### Expected Response:
```json
{
  "totalRevenue": 2.8
}
```

---

## 🔄 **TEST 8: Cancel një Rezervim**

### Request:
- **Method:** PATCH
- **URL:** `http://localhost:9090/api/reservations/1/cancel`

### Expected Response:
```json
{
  "id": 1,
  "status": "CANCELLED",
  ...
}
```

---

## 🔍 **TEST 9: Search Users by Name**

### Request:
- **Method:** GET
- **URL:** `http://localhost:9090/api/users/search?keyword=Arber`

### Expected Response:
Lista e users që përmbajnë "Arber" në emër

---

## 📅 **TEST 10: Shiko Reservations by Status**

### Request:
- **Method:** GET
- **URL:** `http://localhost:9090/api/reservations/status/ACTIVE`

### Expected Response:
Lista e rezervimeve ACTIVE

---

## 🗄️ **PJESA 3: VERIFIKIMI NË DATABASE (H2 Console)**

### 1. Hap në Browser:
```
http://localhost:9090/h2-console
```

### 2. Login Info:
- **JDBC URL:** `jdbc:h2:mem:smartparkingdb`
- **Username:** `sa`
- **Password:** (leave empty)
- **Click:** "Connect"

### 3. SQL Queries për Testing:

#### **Shiko të gjithë users:**
```sql
SELECT * FROM users;
```

#### **Shiko vehicles me user names:**
```sql
SELECT v.id, v.license_plate, v.type, u.name AS owner_name
FROM vehicles v
JOIN users u ON v.user_id = u.id;
```

#### **Shiko free parking spots:**
```sql
SELECT ps.id, ps.spot_number, ps.status, pl.name AS parking_lot_name
FROM parking_spots ps
JOIN parking_lots pl ON ps.parking_lot_id = pl.id
WHERE ps.status = 'FREE';
```

#### **Shiko active reservations:**
```sql
SELECT r.id, u.name AS user_name, v.license_plate, ps.spot_number, 
       r.start_time, r.end_time, r.price, r.status
FROM reservations r
JOIN users u ON r.user_id = u.id
JOIN vehicles v ON r.vehicle_id = v.id
JOIN parking_spots ps ON r.spot_id = ps.id
WHERE r.status = 'ACTIVE';
```

#### **Shiko total revenue:**
```sql
SELECT SUM(amount) AS total_revenue 
FROM payments 
WHERE status = 'COMPLETED';
```

#### **Shiko active subscriptions:**
```sql
SELECT s.id, u.name AS user_name, s.start_date, s.end_date, 
       s.monthly_fee, s.status
FROM subscriptions s
JOIN users u ON s.user_id = u.id
WHERE s.status = 'ACTIVE';
```

---

## 🎯 **TESTI I PLOTË - FULL SCENARIO**

### Scenario: Një klient rezervon një vend parkimi dhe paguan

#### **Hapi 1:** Shiko available spots
```
GET http://localhost:9090/api/parking-spots/available
```

#### **Hapi 2:** Krijo rezervim
```
POST http://localhost:9090/api/reservations
Body: {
  "userId": 2,
  "parkingSpotId": 1,
  "vehicleId": 2,
  "startTime": "2024-02-01T10:00:00",
  "endTime": "2024-02-01T12:00:00"
}
```

#### **Hapi 3:** Shiko çmimin (price në response)
```
Çmimi llogaritet automatikisht bazuar në:
- Kohëzgjatje (2 hours)
- Vehicle type (ELECTRIC = -10%)
- Subscription (User 2 = Arber ka subscription = -30%)
Result: 2 × 2.0 × 0.9 × 0.7 = 2.52€
```

#### **Hapi 4:** Krijo payment
```
POST http://localhost:9090/api/payments
Body: {
  "amount": 2.52,
  "method": "CREDIT_CARD",
  "reservationId": 1
}
```

#### **Hapi 5:** Verifikimi
```
GET http://localhost:9090/api/reservations/1
GET http://localhost:9090/api/payments/1
GET http://localhost:9090/api/payments/revenue/total
```

---

## ✅ **CHECKLIST - Çfarë Duhet të Funksionojë:**

- [ ] GET /api/users → Shfaq 4 users
- [ ] POST /api/users → Krijon user të ri
- [ ] GET /api/vehicles → Shfaq 4 vehicles
- [ ] GET /api/parking-spots/available → Shfaq FREE spots
- [ ] POST /api/reservations → Krijon rezervim dhe llogarit çmim
- [ ] POST /api/payments → Procesion payment
- [ ] GET /api/payments/revenue/total → Shfaq total revenue
- [ ] PATCH /api/reservations/{id}/cancel → Anulon rezervim
- [ ] H2 Console → Mund të shikosh tabelat
- [ ] SQL Queries → Funksionojnë në H2 Console

---

## 🎤 **PËR DEMO:**

### Sample Data që Ekziston:

**Users:**
- ID 1: Admin (admin@smartparking.com)
- ID 2: Arber (arber@example.com) - ka subscription
- ID 3: Liridona (liridona@example.com)
- ID 4: Arta (arta@example.com)

**Vehicles:**
- ID 1: PR-1234-AA (CAR) - Owner: Arber
- ID 2: PR-5678-BB (ELECTRIC) - Owner: Arber
- ID 3: PR-9012-CC (MOTORCYCLE) - Owner: Liridona
- ID 4: PR-3456-DD (CAR) - Owner: Arta

**Parking Lots:**
- ID 1: Parking Qendra (200 spots)
- ID 2: Parking Grand (150 spots)
- ID 3: Parking Prishtina Mall (300 spots)

**Parking Spots:** 10 total (të gjitha FREE)

---

## 📝 **NOTA:**

- Të gjitha URLs fillojnë me: `http://localhost:9090`
- Për POST/PUT/DELETE përdor Postman ose curl
- Për GET mund të përdorësh browser direkt
- Database është in-memory (të dhënat humben kur mbyll aplikacionin)

---

**GOOD LUCK ME TESTIMIN!** 🚀
