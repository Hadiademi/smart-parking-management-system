# PostgreSQL Setup Guide

## Hapi 1: Krijo Database në PgAdmin

1. **Hap PgAdmin**
2. **Connect** në PostgreSQL server (localhost)
3. **Right-click** në "Databases" → "Create" → "Database"
4. **Database Name**: `smartparkingdb`
5. **Owner**: postgres (default)
6. **Click "Save"**

### Ose përmes SQL:

```sql
CREATE DATABASE smartparkingdb;
```

## Hapi 2: Verifiko Kredencialet

Kredencialet tuaja PostgreSQL (zakonisht):
- **Host**: localhost
- **Port**: 5432
- **Database**: smartparkingdb
- **Username**: postgres
- **Password**: (passwordi që keni vendosur gjatë instalimit)

## Hapi 3: Testo Connection

Në PgAdmin, kliko në `smartparkingdb` → Duhet të hapet pa error.

---

## ✅ Aplikacioni Tani Duhet të Funksionojë!

Mbas që të ndryshoni `application.properties`, Run aplikacionin në IntelliJ.
