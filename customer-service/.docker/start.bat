@echo off
echo ====================================================
echo    Digital Bank Customer Service - Startup Script
echo ====================================================
echo.

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
  echo ERROR: Docker is not running.
  echo Please start Docker Desktop first, then run this script again.
  echo.
  pause
  exit /b 1
)

echo Starting services...
docker-compose up -d

if %errorlevel% neq 0 (
  echo.
  echo ERROR: Failed to start services. Please see error above.
  echo.
  pause
  exit /b 1
)

echo.
echo Services are starting... this may take up to 30 seconds.
echo.

timeout /t 5 /nobreak > nul

echo Application is now available at: http://localhost:9000/swagger-ui/index.htm
echo.
echo To stop the application, run stop.bat
echo.
echo Press any key to open the application in your browser...
pause > nul

start "" http://localhost:9000/swagger-ui/index.htm