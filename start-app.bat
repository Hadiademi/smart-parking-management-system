@echo off
echo ========================================
echo  SMART PARKING - AUTO START
echo ========================================
echo.

echo [1/4] Checking Java...
java -version
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH!
    pause
    exit /b 1
)
echo OK: Java is installed
echo.

echo [2/4] Cleaning project...
call gradlew clean
echo.

echo [3/4] Building project...
call gradlew build -x test
if errorlevel 1 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)
echo OK: Build successful
echo.

echo [4/4] Starting application...
echo.
echo ========================================
echo  WAIT FOR: "Started DemoApplication"
echo  THEN OPEN: http://localhost:9090/api/users
echo ========================================
echo.

call gradlew bootRun

pause
