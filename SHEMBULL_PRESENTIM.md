# ✅ SHEMBULL PËR PREZANTIM (Copy/Paste në Postman)

**Base URL:** `http://localhost:9090/api`

**Postman settings (shumë e rëndësishme):**
- Body → `raw` → zgjidh `JSON`
- Header: `Content-Type: application/json`

---

## 0) (Opsionale) Kontrollo që API po punon

```
GET http://localhost:9090/api/users
```

---

## 1) Krijo **1 USER** (i ri)

```
POST http://localhost:9090/api/users
```

```json
{
  "name": "User Demo",
  "email": "user.demo.2026@gmail.com",
  "password": "Test12345",
  "role": "CLIENT"
}
```

✅ **Ruaje `id` nga response** si **`USER_ID`**

---

## 2) Krijo **1 VEHICLE** për atë user

```
POST http://localhost:9090/api/vehicles
```

```json
{
  "licensePlate": "DEMO-123",
  "type": "CAR",
  "userId": USER_ID
}
```

✅ **Ruaje `id` nga response** si **`VEHICLE_ID`**

---

## 3) Krijo **1 PARKING LOT**

```
POST http://localhost:9090/api/parking-lots
```

```json
{
  "name": "Parking Demo",
  "address": "Prishtine - Qender",
  "totalSpots": 50
}
```

✅ **Ruaje `id` nga response** si **`LOT_ID`**

---

## 4) Krijo **1 PARKING SPOT** në atë lot

```
POST http://localhost:9090/api/parking-spots
```

```json
{
  "spotNumber": "D-001",
  "status": "FREE",
  "parkingLotId": LOT_ID
}
```

✅ **Ruaje `id` nga response** si **`SPOT_ID`**

---

## 5) Krijo **1 RESERVATION** (me kohë në të ardhmen)

**Shembull kohësh (të sigurta):**
- startTime: `2026-01-30T10:00:00`
- endTime: `2026-01-30T12:00:00`

```
POST http://localhost:9090/api/reservations
```

```json
{
  "userId": USER_ID,
  "parkingSpotId": SPOT_ID,
  "vehicleId": VEHICLE_ID,
  "startTime": "2026-01-30T10:00:00",
  "endTime": "2026-01-30T12:00:00"
}
```

✅ **Ruaje `id` nga response** si **`RESERVATION_ID`**  
✅ **Ruaje `price` nga response** si **`RESERVATION_PRICE`**

---

## 6) Krijo **1 PAYMENT** (vetëm me kartë)

⚠️ **`amount` duhet të jetë SAKTËSISHT sa `price` i reservation-it**.

```
POST http://localhost:9090/api/payments
```

```json
{
  "amount": RESERVATION_PRICE,
  "method": "CREDIT_CARD",
  "reservationId": RESERVATION_ID
}
```

---

## (Opsionale) Verifikime të shpejta për prezantim

### Shiko reservation-in:
```
GET http://localhost:9090/api/reservations/RESERVATION_ID
```

### Shiko payment-in e reservation-it:
```
GET http://localhost:9090/api/payments/reservation/RESERVATION_ID
```

### Shiko revenue total:
```
GET http://localhost:9090/api/payments/revenue/total
```

