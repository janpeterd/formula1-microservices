#!/bin/bash

# Base URL
BASE_URL="localhost:8081/api"

add() {
  # Add multiple drivers
  echo "Adding drivers..."
  curl -X POST "$BASE_URL/driver" \
    -H "Content-Type: application/json" \
    -d '{
    "firstName": "Lewis",
    "lastName": "Hamilton",
    "country": "UK",
    "teamCode": "MERC",
    "seasonPoints": 370
  }'

  curl -X POST "$BASE_URL/driver" \
    -H "Content-Type: application/json" \
    -d '{
    "firstName": "Max",
    "lastName": "Verstappen",
    "country": "Netherlands",
    "teamCode": "RBR",
    "seasonPoints": 454
  }'

  curl -X POST "$BASE_URL/driver" \
    -H "Content-Type: application/json" \
    -d '{
    "firstName": "Charles",
    "lastName": "Leclerc",
    "country": "Monaco",
    "teamCode": "FER",
    "seasonPoints": 201
  }'

}

get() {
  # Get all drivers
  curl -X GET "$BASE_URL/driver/all"
}

update() {
  # Update an item
  echo "Enter driver code: "
  read -r CODE
  curl -X PUT "$BASE_URL/driver/$CODE" \
    -H "Content-Type: application/json" \
    -d '{
    "id": 1,
    "firstName": "Lewis UPDATED",
    "lastName": "Hamilton UPDATED",
    "country": "UK",
    "teamCode": "FER (UPDATED)",
    "seasonPoints": 201
  },
'

}

delete() {
  # Delete an item
  echo "Deleting driver with ID 1..."
  curl -X DELETE "$BASE_URL/drivers/1"
}

case "$1" in
"add") add ;;
"update") update ;;
"get") get ;;
"delete") get ;;
*) get ;;
esac
