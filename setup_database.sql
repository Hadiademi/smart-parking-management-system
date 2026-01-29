-- ============================================
-- Smart Parking System - Database Setup Script
-- ============================================
-- UDHËZIME:
-- 1. Hap PgAdmin
-- 2. Right-click në PostgreSQL server → Query Tool
-- 3. Copy-paste GJITHË këtë file
-- 4. Click ▶️ (Execute/Run)
-- 5. GATI!
-- ============================================

-- Create database (nëse nuk ekziston)
SELECT 'CREATE DATABASE smartparkingdb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'smartparkingdb')\gexec

-- Note: Pas krijimit të database, duhet të lidhesh me smartparkingdb
-- dhe të ekzekutosh pjesën tjetër të script-it

-- ============================================
-- STOP KËTU!
-- Pas ekzekutimit të këtij komandi:
-- 1. Close Query Tool
-- 2. Refresh Databases në PgAdmin (F5)
-- 3. Kliko në "smartparkingdb"
-- 4. Tools → Query Tool (hap një query tool të ri)
-- 5. Tani RUN aplikacionin në IntelliJ
--    (Spring Boot do të krijojë tabelat automatikisht!)
-- ============================================

-- Spring Boot me spring.jpa.hibernate.ddl-auto=update
-- do t'i krijojë automatikisht të gjitha tabelat!
-- DataInitializer.java do të populojë të dhënat!

-- ============================================
-- VERIFIKIMI (Run pas startimit të aplikacionit)
-- ============================================

-- Shiko users
-- SELECT * FROM users;

-- Shiko vehicles
-- SELECT * FROM vehicles;

-- Shiko parking lots
-- SELECT * FROM parking_lots;

-- Shiko parking spots
-- SELECT * FROM parking_spots;

-- Shiko subscriptions
-- SELECT * FROM subscriptions;
