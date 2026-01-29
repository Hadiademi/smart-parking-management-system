# 🔧 Git Setup - Smart Parking System

## Hapat për të inicializuar Git Repository:

### Hapi 1: Hap Terminal në IntelliJ

1. **View** → **Tool Windows** → **Terminal** (ose Alt+F12)
2. Siguروhu që je në directory: `C:\Users\HP\Downloads\demo\demo`

---

### Hapi 2: Inicializo Git

```bash
git init
```

---

### Hapi 3: Add të gjitha files

```bash
git add .
```

---

### Hapi 4: Krijo commit të parë

```bash
git commit -m "Initial commit: Smart Parking Management System

Features:
- 7 entities with complete relationships
- Full REST API with 40+ endpoints
- Business logic with automatic price calculation
- 36+ unit tests (JUnit + Mockito)
- Exception handling and validation
- DTO mapping with ModelMapper
- Sample data initialization
- Comprehensive documentation

Technologies:
- Spring Boot 4.0.2
- Spring Data JPA
- H2 Database
- Lombok
- ModelMapper
- JUnit 5 & Mockito"
```

---

### Hapi 5: Shiko Git Log

```bash
git log --oneline
```

---

## 📊 Statistika të Projektit:

```bash
# Shiko sa lines of code ka
find src -name "*.java" | xargs wc -l

# Shiko strukturën e projektit
tree /f /a
```

---

## 🌐 Push në GitHub (Optional):

### 1. Krijo Repository në GitHub
- Shko te github.com
- Click "New Repository"
- Name: `smart-parking-system`
- Click "Create repository"

### 2. Connect Local to GitHub

```bash
git remote add origin https://github.com/YOUR_USERNAME/smart-parking-system.git
git branch -M main
git push -u origin main
```

---

## ✅ Git është gati!

Tani projekti juaj është në version control dhe mund të bëni commits.

### Për commits të ardhshëm:

```bash
# Shiko ndryshimet
git status

# Add ndryshime
git add .

# Commit
git commit -m "Description of changes"

# Push to GitHub
git push
```
