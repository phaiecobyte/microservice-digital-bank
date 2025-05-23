#!/bin/bash

echo "===================================================="
echo "    Digital Bank Customer Service - Startup Script"
echo "===================================================="
echo

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
  echo "ERROR: Docker is not running."
  echo "Please start Docker Desktop first, then run this script again."
  echo
  read -p "Press Enter to continue..."
  exit 1
fi

echo "Starting services..."
docker-compose up -d

if [ $? -ne 0 ]; then
  echo
  echo "ERROR: Failed to start services. Please see error above."
  echo
  read -p "Press Enter to continue..."
  exit 1
fi

echo
echo "Services are starting... this may take up to 30 seconds."
echo

sleep 5

echo "Application is now available at: http://localhost:9000/swagger-ui/index.htm"
echo
echo "To stop the application, run ./stop.sh"
echo

# Make the script executable
chmod +x stop.sh

# For macOS/Linux, try to open in browser
if command -v xdg-open > /dev/null; then
  read -p "Press Enter to open the application in your browser..."
  xdg-open "http://localhost:9000"
elif command -v open > /dev/null; then
  read -p "Press Enter to open the application in your browser..."
  open "http://localhost:9000"
else
  echo "Please open http://localhost:9000 in your browser."
  read -p "Press Enter to continue..."
fi