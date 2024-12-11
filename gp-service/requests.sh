#!/bin/bash

# Base URL
BASE_URL="localhost:8082/api"

add() {
  # Add a gp
  echo "Adding gp..."
  curl -X POST "$BASE_URL/gp" \
    -H "Content-Type: application/json" \
    -d '{
      "gpCode": "CIR001",
      "name": "Monaco Grand Prix",
      "country": "Monaco",
      "distanceMeters": 3371,
      "laps": 78
    }'

  curl -X POST "$BASE_URL/gp" \
    -H "Content-Type: application/json" \
    -d '{
      "gpCode": "CIR002",
      "name": "Silverstone Gp",
      "country": "UK",
      "distanceMeters": 5891,
      "laps": 52
    }'

  curl -X POST "$BASE_URL/gp" \
    -H "Content-Type: application/json" \
    -d '{
      "gpCode": "CIR003",
      "name": "Spa-Francorchamps",
      "country": "Belgium",
      "distanceMeters": 7004,
      "laps": 44
    }'
}

get() {
  # Get all gps
  echo "Fetching all gps..."
  curl -X GET "$BASE_URL/gp"
}

case "$1" in
"add") add ;;
"get") get ;;
*) get ;;
esac
