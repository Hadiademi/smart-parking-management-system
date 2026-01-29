# 🚀 UDHËZIME TË THJESHTA - VETËM 3 HAPA!

## ✅ Gjithçka që Duhet të Bësh:

---

## **HAPI 1: Hap PgAdmin** 🔓

1. **Search në Windows**: Shkruaj "PgAdmin"
2. **Hap PgAdmin 4**
3. **Vendos passwordin**: *(passwordi yt i PostgreSQL)* (kur të pyet)
4. **Prit** të hapet dritarja

---

## **HAPI 2: Krijo Database** 📊

### Zgjedhja 1 - Përmes Menusë (MË E LEHTË):

1. **Në anën e majtë**, shiko për:
   ```
   Servers
     └─ PostgreSQL 15 (ose 14, 13, etj.)
         └─ Databases
   ```

2. **Right-click** në **"Databases"**

3. **Zgjedh**: **Create** → **Database...**

4. **Në dritaren që hapet**:
   - **Database**: Shkruaj `smartparkingdb`
   - **Owner**: Lëre "postgres" (default)
   - **Mos prek asgjë tjetër!**

5. **Click "Save"** (poshtë djathtas)

6. **GATI!** Duhet të shohësh `smartparkingdb` në listë!

---

### Zgjedhja 2 - Përmes SQL (Alternativë):

1. **Click** në "PostgreSQL 15" (ose version tjetër)

2. **Në menu lart**: **Tools** → **Query Tool**
   - Ose shtyp **F5**

3. **Në dritaren e bardhë që hapet**, shkruaj VETËM këtë:
   ```sql
   CREATE DATABASE smartparkingdb;
   ```

4. **Click ▶️** (butoni i gjelbër lart) ose shtyp **F5**

5. **Duhet të shohësh**: "CREATE DATABASE" në fund

6. **Close** Query Tool

7. **Refresh**: Right-click "Databases" → Refresh (ose F5)

8. **GATI!** Duhet të shohësh `smartparkingdb` në listë!

---

## **HAPI 3: Run Aplikacionin në IntelliJ** 🎯

### A. Hap Projektin (nëse nuk është i hapur)

1. **Hap IntelliJ IDEA**
2. **File** → **Open**
3. **Navigate** te: `C:\Users\HP\Downloads\demo\demo`
4. **Click "OK"**
5. **Prit** 2-3 minuta që të shkarkojë dependencies (shiko progress bar poshtë)

---

### B. Run Aplikacionin

1. **Në anën e majtë**, expand folders:
   ```
   demo
     └─ src
         └─ main
             └─ java
                 └─ com.example.demo
                     └─ DemoApplication.java
   ```

2. **Double-click** në `DemoApplication.java` (të hapet në editor)

3. **Right-click** kudo në kod

4. **Click**: **Run 'DemoApplication.main()'**
   - Ose kliko ikonën ▶️ gjelbër pranë `public class DemoApplication`

5. **Shiko Console-in poshtë** - duhet të fillojë të shfaqë tekst!

---

## **✅ SI TA DISH QË FUNKSIONON:**

### Në Console (IntelliJ), duhet të shohësh:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

... (më shumë tekst) ...

🚀 Initializing Sample Data...
✅ Created 4 users (1 admin, 3 clients)
✅ Created 4 vehicles
✅ Created 3 parking lots
✅ Created 10 parking spots across 3 parking lots
✅ Created 2 subscriptions (1 active, 1 expired)

✨ Sample Data Initialization Complete!

📊 Database Summary:
   - Users: 4
   - Vehicles: 4
   - Parking Lots: 3
   - Parking Spots: 10
   - Subscriptions: 2

🎯 Quick Test Credentials:
   Admin: admin@smartparking.com / admin123
   Client: arber@example.com / password123

🌐 Access Points:
   - API: http://localhost:8080/api

Started DemoApplication in X.XXX seconds
```

---

## **🎉 VERIFIKIMI - Kontrollo që Gjithçka Funksionon:**

### 1. **Në Browser**

Hap Chrome/Firefox dhe shko te:
```
http://localhost:8080/api/users
```

**Duhet të shohësh** JSON me 4 users:
```json
[
  {
    "id": 1,
    "name": "Admin User",
    "email": "admin@smartparking.com",
    ...
  },
  ...
]
```

---

### 2. **Në PgAdmin**

1. **Expand** në anën e majtë:
   ```
   Servers
     └─ PostgreSQL 15
         └─ Databases
             └─ smartparkingdb
                 └─ Schemas
                     └─ public
                         └─ Tables
   ```

2. **Duhet të shohësh** tabela:
   - `users`
   - `vehicles`
   - `parking_lots`
   - `parking_spots`
   - `reservations`
   - `payments`
   - `subscriptions`

3. **Right-click** në `users` → **View/Edit Data** → **All Rows**

4. **Duhet të shohësh 4 users!**

---

## **📱 TESTO API:**

### Në Browser, provo këto URL:

1. **Të gjithë users**:
   ```
   http://localhost:8080/api/users
   ```

2. **Parking spots available**:
   ```
   http://localhost:8080/api/parking-spots/available
   ```

3. **Të gjithë parking lots**:
   ```
   http://localhost:8080/api/parking-lots
   ```

4. **Vehicles**:
   ```
   http://localhost:8080/api/vehicles
   ```

---

## **🚨 PROBLEME TË MUNDSHME:**

### Problem 1: "database does not exist"
**Zgjidhje**: Kthehu te HAPI 2 dhe krijo `smartparkingdb`

### Problem 2: "password authentication failed"  
**Zgjidhje**: Vendos passwordin e saktë të PostgreSQL në konfigurim.

### Problem 3: "Port 8080 already in use"
**Zgjidhje**: 
- Një aplikacion tjetër po përdor port 8080
- Mbyll aplikacione të tjera
- Ose ndrysho port në `application.properties`: `server.port=8081`

### Problem 4: IntelliJ nuk po shkarkojë dependencies
**Zgjidhje**:
- Prit 5 minuta
- Right-click project → Gradle → Reload Gradle Project
- File → Invalidate Caches → Invalidate and Restart

---

## **📞 NDIHMË:**

Nëse vazhdon problemi, më thuaj:
1. ✅ A e ke krijuar `smartparkingdb` në PgAdmin?
2. ✅ A shfaqet ndonjë error në console të IntelliJ?
3. ✅ Çfarë shkruan në console kur bën Run?

---

## **🎯 RECAP - Hapat në SHKURT:**

1. ✅ Hap PgAdmin → Vendos passwordin tënd të PostgreSQL
2. ✅ Krijo database `smartparkingdb`
3. ✅ Run `DemoApplication` në IntelliJ
4. ✅ Shiko console - duhet të thotë "Sample Data Complete!"
5. ✅ Test në browser: `http://localhost:8080/api/users`

---

## **🌟 PAS QË FUNKSIONON:**

- 📖 Lexo `API_EXAMPLES.md` për të testuar endpoints
- 📖 Lexo `README.md` për dokumentacion të plotë
- 🧪 Testo rezervime, pagesa, etj.
- 🎤 Përgatitu për demo!

---

**GOOD LUCK!** 🚀✨

Nëse ke ndonjë problem, më thuaj dhe do të të ndihmoj MENJËHERË!
