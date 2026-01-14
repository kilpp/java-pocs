#!/bin/bash

echo "===================================="
echo "Spring Pulsar POC - Quick Start"
echo "===================================="
echo ""

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "Docker is not running. Please start Docker first."
    exit 1
fi

echo "📦 Starting Pulsar using Docker Compose..."
docker-compose up -d

echo ""
echo "⏳ Waiting for Pulsar to be ready..."
sleep 10

# Wait for Pulsar to be healthy
max_attempts=30
attempt=0
while [ $attempt -lt $max_attempts ]; do
    if docker exec pulsar-standalone bin/pulsar-admin brokers healthcheck > /dev/null 2>&1; then
        echo "Pulsar is ready!"
        break
    fi
    echo "   Still waiting... ($attempt/$max_attempts)"
    sleep 2
    attempt=$((attempt + 1))
done

if [ $attempt -eq $max_attempts ]; then
    echo "Pulsar failed to start within the timeout period"
    exit 1
fi

echo ""
echo "🚀 Starting Spring Boot application..."
./gradlew bootRun &
SPRING_PID=$!

echo ""
echo "===================================="
echo "✅ Setup Complete!"
echo "===================================="
echo ""
echo "📌 Pulsar Admin UI: http://localhost:8080"
echo "📌 Spring Boot API:  http://localhost:8081"
echo ""
echo "🧪 Test the API:"
echo "   curl http://localhost:8081/api/messages/test"
echo ""
echo "📨 Send a message:"
echo '   curl -X POST http://localhost:8081/api/messages/send \'
echo '        -H "Content-Type: application/json" \'
echo '        -d '"'"'{"content": "Hello Pulsar!"}'"'"
echo ""
echo "🛑 To stop everything, press Ctrl+C and run:"
echo "   docker-compose down"
echo ""

