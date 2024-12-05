#!/bin/bash

# Base URL
BASE_URL="localhost:8082/api"

add() {
  # Add a circuit
  echo "Adding circuit..."
  curl -X POST "$BASE_URL/circuit" \
    -H "Content-Type: application/json" \
    -d '{
      "circuitCode": "CIR001",
      "name": "Monaco Grand Prix",
      "country": "Monaco",
      "distanceMeters": 3371,
      "laps": 78
    }'

  curl -X POST "$BASE_URL/circuit" \
    -H "Content-Type: application/json" \
    -d '{
      "circuitCode": "CIR002",
      "name": "Silverstone Circuit",
      "country": "UK",
      "distanceMeters": 5891,
      "laps": 52
    }'

  curl -X POST "$BASE_URL/circuit" \
    -H "Content-Type: application/json" \
    -d '{
      "circuitCode": "CIR003",
      "name": "Spa-Francorchamps",
      "country": "Belgium",
      "distanceMeters": 7004,
      "laps": 44
    }'
}

get() {
  # Get all circuits
  echo "Fetching all circuits..."
  curl -X GET "$BASE_URL/circuit"
}

case "$1" in
"add") add ;;
"get") get ;;
*) get ;;
esac
