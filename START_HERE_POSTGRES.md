# 🚀 SI TA STARTONI ME POSTGRESQL

## HAPI 1: Krijo Database

### Mënyra 1: Përmes PgAdmin (më e lehtë)

1. **Hap PgAdmin 4**
2. **Vendos password** e PostgreSQL (nëse të pyet)
3. **Në anën e majtë**: Expand "Servers" → "PostgreSQL"
4. **Right-click** në "Databases"
5. **Create** → **Database...**
6. **Database name**: Shkruaj `smartparkingdb`
7. **Click "Save"**

### Mënyra 2: Përmes SQL

1. Hap PgAdmin
2. Click në "PostgreSQL" server
3. Tools → Query Tool
4. Copy-paste këtë:
```sql
CREATE DATABASE smartparkingdb;
```
5. Click ▶️ (Execute)

---

## HAPI 2: Ndrysho Password (SHUMË E RËNDËSISHME!)

1. **Hap këtë file**: `src/main/resources/application.properties`

2. **Gjej këtë line**:
```properties
spring.datasource.password=postgres
```

3. **Ndrysho** `postgres` me password-in tuaj **REAL** të PostgreSQL
   - Nëse nuk e mbani mend, është passwordi që vendosët kur instaluat PostgreSQL
   - Zakonisht është: `postgres`, `admin`, `root`, ose diçka që ju vendosët

**SHEMBULL:**
```properties
# Nëse passwordi juaj është "mypassword123"
spring.datasource.password=mypassword123
```

4. **RUAJ** filen (Ctrl+S)

---

## HAPI 3: Run Aplikacionin

### Në IntelliJ IDEA:

1. **Hap projektin** në IntelliJ (nëse nuk është hapur)
   - File → Open → Zgjidh folderin: `c:\Users\HP\Downloads\demo\demo`

2. **Prit** që IntelliJ të përfundojë indexing (shih progress në fund të dritares)

3. **Gjej file-in**: `DemoApplication.java`
   - Mund ta gjesh në: `src/main/java/com/example/demo/DemoApplication.java`

4. **Right-click** në `DemoApplication.java`

5. **Click** "Run 'DemoApplication.main()'"

6. **Shiko console-in** poshtë - duhet të shohësh:
```
🚀 Initializing Sample Data...
✅ Created 4 users
✅ Created 4 vehicles
✅ Created 3 parking lots
✅ Created 10 parking spots
✅ Created 2 subscriptions

✨ Sample Data Initialization Complete!
```

7. **SUCCESS!** Aplikacioni është duke punuar!

---

## ✅ Si të Verifikosh që Funksionon:

### 1. Shiko në PgAdmin

1. Hap PgAdmin
2. Expand: Servers → PostgreSQL → Databases → **smartparkingdb**
3. Right-click → Query Tool
4. Shkruaj:
```sql
SELECT * FROM users;
```
5. Click ▶️
6. **Duhet të shohësh 4 users!**

### 2. Testo API

Hap browser dhe shko te:
```
http://localhost:8080/api/users
```

Duhet të shohësh JSON me users:
```json
[
  {
    "id": 1,
    "name": "Admin User",
    "email": "admin@smartparking.com",
    ...
  }
]
```

---

## 🐛 PROBLEME TË MUNDSHME:

### Problem 1: "Connection refused" ose "could not connect"

**ZGJIDHJE:**
- ✅ Sigurohu që PostgreSQL është duke punuar
- Në Windows: Services → PostgreSQL duhet të jetë "Running"
- Në PgAdmin: Duhet të mund të lidhesh me server

### Problem 2: "password authentication failed"

**ZGJIDHJE:**
- ❌ Passwordi në `application.properties` është gabim
- ✅ Hap `application.properties` dhe vendos password-in e saktë
- ✅ Nëse nuk e di passwordin, mund ta resetosh në PostgreSQL

### Problem 3: "database does not exist"

**ZGJIDHJE:**
- ❌ Nuk e ke krijuar database-in `smartparkingdb`
- ✅ Kthehu te HAPI 1 dhe krijoje

### Problem 4: IntelliJ po thotë "Cannot resolve symbol"

**ZGJIDHJE:**
- ✅ Right-click në projekt → Gradle → Reload Gradle Project
- ✅ Prit 2-3 minuta që të shkarkojë dependencies
- ✅ Provo sërish

---

## 📞 Si të Më Kontaktosh nëse Vazhdon Problemi:

Më trego:
1. Çfarë errori po të del (exact message)
2. Screenshot të console-it në IntelliJ
3. A e ke krijuar database-in në PgAdmin?
4. A funksionon PostgreSQL (PostgreSQL service running)?

---

## 🎯 Pas që Funksionon:

1. **Testo API endpoints** - Shiko `API_EXAMPLES.md`
2. **Krijo rezervime** - Testo business logic
3. **Shiko në PgAdmin** - Vërej si të dhënat ruhen
4. **Përgatitu për demo** - Shiko `PROJECT_SUMMARY.md`

---

## ⚡ Quick Reference:

**Database Name**: `smartparkingdb`
**Username**: `postgres`
**Password**: (që ti ke vendosur)
**Port**: `5432`
**Host**: `localhost`

**API URL**: `http://localhost:8080/api`

---

GOOD LUCK! 🚀
