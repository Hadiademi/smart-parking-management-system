# 🔧 TROUBLESHOOTING - Zgjidhja e Problemeve

## 🔍 DIAGNOSTIKIMI

### Problem 1: "Connection Failed" në Browser

**Shkaku:**
- Aplikacioni nuk është duke u ekzekutuar në IntelliJ

**Zgjidhja:**
1. Shiko IntelliJ Console
2. Kërko për: "Started DemoApplication"
3. Nëse NUK e shikon → Aplikacioni nuk ka startuar

**Fix:**
```
1. Stop aplikacionin (buton i kuq)
2. Run DemoApplication.java sërish
3. PRIT 1 minutë
4. Pastaj provo URL-në
```

---

### Problem 2: Port 9090 Already in Use

**Shkaku:**
- Një aplikacion tjetër po përdor port 9090
- Ose një instance e vjetër ende po ekzekutohet

**Zgjidhja 1 - Gjej dhe mbyll:**
```powershell
# Në PowerShell:
netstat -ano | findstr :9090

# Pastaj kill process:
taskkill /PID <PID_NUMBER> /F
```

**Zgjidhja 2 - Ndrysho port:**
Në `application.properties` line 27:
```properties
server.port=8081  # Ose ndonjë port tjetër
```

---

### Problem 3: Gradle Build Failed

**Shkaku:**
- Dependencies nuk janë shkarkuar
- Network issues

**Zgjidhja:**
```bash
# Clean dhe rebuild:
./gradlew clean build --refresh-dependencies

# Nëse vazhdon:
# 1. Delete folder: .gradle
# 2. IntelliJ: File → Invalidate Caches → Invalidate and Restart
# 3. Provo sërish
```

---

### Problem 4: "Cannot resolve symbol" në IntelliJ

**Shkaku:**
- Lombok annotations nuk po njihen
- IntelliJ cache issue

**Zgjidhja:**
```
1. File → Settings → Plugins
2. Search: "Lombok"
3. Install Lombok plugin
4. Restart IntelliJ
5. File → Invalidate Caches → Invalidate and Restart
```

---

### Problem 5: Database Connection Error

**Shkaku:**
- H2 database config gabim (NUK duhet të ndodhë)

**Zgjidhja:**
Kontrollo `application.properties`:
```properties
spring.datasource.url=jdbc:h2:mem:smartparkingdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

---

### Problem 6: Tests Failing

**Shkaku:**
- Mock issues në tests

**Zgjidhja:**
```bash
# Skip tests për tani:
./gradlew build -x test

# Ose run tests individually në IntelliJ
```

---

### Problem 7: "Whitelabel Error Page"

**Shkaku:**
- Po aksesohet endpoint që nuk ekziston

**Zgjidhja:**
Sigurohu që po përdor URL-të e sakta:
```
✓ http://localhost:9090/api/users
✗ http://localhost:9090/users (gabim)

✓ http://localhost:9090/h2-console
✗ http://localhost:9090/console (gabim)
```

---

### Problem 8: Empty Response në Browser

**Shkaku:**
- Aplikacioni filloi por DataInitializer nuk u ekzekutua

**Zgjidhja:**
1. Stop aplikacionin
2. Në `application.properties`, kontrollo:
```properties
spring.jpa.hibernate.ddl-auto=create-drop
```
3. Run sërish

---

### Problem 9: Java Version Mismatch

**Shkaku:**
- Java version gabim

**Kontrollo:**
```bash
java -version
# Duhet të jetë: Java 17 ose më lart
```

**Fix:**
```
1. Download Java 17: https://adoptium.net/
2. Install
3. IntelliJ: File → Project Structure → SDK → Add JDK
4. Select Java 17
```

---

### Problem 10: Slow Startup

**Normal:**
- Herën e parë mund të marrë 2-3 minuta
- Gradle shkarkojnë dependencies

**Zgjidhja:**
- Thjesht PRIT!
- Mos e ndalo në mes

---

## 📋 CHECKLIST - Para se të më pyesësh:

- [ ] A e kam startuar aplikacionin në IntelliJ?
- [ ] A po shoh "Started DemoApplication" në console?
- [ ] A kam pritur të paktën 1 minutë pas startimit?
- [ ] A po përdor URL-në e saktë: `http://localhost:9090/api/users`?
- [ ] A është port 9090 i lirë (jo i zënë)?
- [ ] A kam Java 17 installed?
- [ ] A janë dependencies të shkarkuara (Gradle sync complete)?

---

## 🆘 SI TË DËRGOSH HELP REQUEST:

Nëse vazhdon problemi, më dërgo:

1. **Screenshot të IntelliJ Console** (e gjithë console)
2. **Exact error message** (copy-paste, jo screenshot)
3. **Java version**: Output of `java -version`
4. **Gradle version**: Output of `gradlew --version`
5. **Çfarë po bën kur ndodh error**: (Starting? Testing URL?)

---

## 🔄 NUCLEAR OPTION - Full Reset:

Nëse asgjë nuk punon:

```bash
# 1. Delete build artifacts:
rm -rf build/
rm -rf .gradle/

# 2. Clean Gradle:
./gradlew clean

# 3. Invalidate IntelliJ:
# File → Invalidate Caches → Invalidate and Restart

# 4. Rebuild:
./gradlew build -x test

# 5. Run:
# Right-click DemoApplication.java → Run
```

---

## ✅ VERIFIKIMI - Si ta dish që funksionon:

### 1. Console Output:
```
Tomcat started on port 9090 (http)
🚀 Initializing Sample Data...
✅ Created 4 users
✅ Created 4 vehicles
...
✨ Sample Data Initialization Complete!
Started DemoApplication in X.XXX seconds
```

### 2. Browser Test:
URL: `http://localhost:9090/api/users`

Response:
```json
[
  {
    "id": 1,
    "name": "Admin User",
    ...
  }
]
```

### 3. H2 Console Test:
URL: `http://localhost:9090/h2-console`
Login successful dhe mund të shikosh tabelat

---

## 📞 KONTAKT:

Nëse ke provuar GJITHÇKA dhe vazhdon:
- Më dërgo info sipas "SI TË DËRGOSH HELP REQUEST"
- Do të ta sistemoj 100%!

---

**Stay calm dhe ndiq hapat sistematikisht!** 🧘‍♂️
