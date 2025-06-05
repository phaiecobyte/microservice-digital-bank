#!/bin/bash
echo "Stopping Digital Bank Customer Service..."
docker-compose down
echo
echo "Services stopped."
echo
read -p "Press Enter to continue..."