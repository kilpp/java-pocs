# Spring Pulsar POC

This is a proof-of-concept project demonstrating Spring Boot integration with Apache Pulsar.

## Project Structure

- **DemoMessage**: POJO model representing messages
- **PulsarConfig**: Configuration for Pulsar schema resolution
- **MessageProducer**: Service for sending messages to Pulsar
- **MessageConsumer**: Listener for consuming messages from Pulsar
- **MessageController**: REST API for triggering message production

## Prerequisites

1. **Java 25** (as configured in build.gradle)
2. **Apache Pulsar** running locally

## Running Apache Pulsar

You can run Pulsar using Docker:

```bash
docker run -it \
  -p 6650:6650 \
  -p 8080:8080 \
  --name pulsar-standalone \
  apachepulsar/pulsar:latest \
  bin/pulsar standalone
```

Or using Pulsar standalone:

```bash
# Download Pulsar
wget https://archive.apache.org/dist/pulsar/pulsar-3.1.0/apache-pulsar-3.1.0-bin.tar.gz
tar xvfz apache-pulsar-3.1.0-bin.tar.gz
cd apache-pulsar-3.1.0

# Start Pulsar standalone
bin/pulsar standalone
```

## Configuration

The application is configured in `src/main/resources/application.properties`:

```properties
# Pulsar service URLs
spring.pulsar.client.service-url=pulsar://localhost:6650
spring.pulsar.admin.service-url=http://localhost:8080

# Topic configuration
spring.pulsar.producer.topic-name=persistent://public/default/demo-topic

# Consumer subscription
spring.pulsar.consumer.subscription.name=demo-subscription
spring.pulsar.consumer.subscription.type=shared
```

## Running the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080` (Note: Pulsar admin uses port 8080 by default, you may need to change the server port if there's a conflict).

## API Endpoints

### 1. Test Endpoint
```bash
curl http://localhost:8080/api/messages/test
```

### 2. Send Simple Message
```bash
curl -X POST http://localhost:8080/api/messages/send \
  -H "Content-Type: application/json" \
  -d '{"content": "Hello from Spring Pulsar!"}'
```

### 3. Send Message with Key
```bash
curl -X POST http://localhost:8080/api/messages/send-with-key \
  -H "Content-Type: application/json" \
  -d '{"content": "Message with key", "key": "user-123"}'
```

## How It Works

1. **Producer**: The `MessageProducer` service uses `PulsarTemplate` to send messages to Pulsar topics.
2. **Consumer**: The `MessageConsumer` class has `@PulsarListener` annotated methods that automatically consume messages from specified topics.
3. **Schema**: Messages are serialized/deserialized using JSON schema configured in `PulsarConfig`.

## Features Demonstrated

- ✅ Sending messages to Pulsar topics
- ✅ Consuming messages with `@PulsarListener`
- ✅ JSON schema serialization
- ✅ Message keys for partitioning
- ✅ Async message sending with callbacks
- ✅ Accessing message metadata
- ✅ REST API integration

## Topics Used

- `persistent://public/default/demo-topic` - Main demo topic
- `persistent://public/default/demo-topic-with-metadata` - Topic for demonstrating metadata access

## Troubleshooting

### Port Conflict
If port 8080 is used by Pulsar admin, add this to `application.properties`:
```properties
server.port=8081
```

### Connection Issues
Ensure Pulsar is running and accessible at:
- Binary protocol: `pulsar://localhost:6650`
- Admin API: `http://localhost:8080`

### View Pulsar Topics
```bash
# List topics
docker exec -it pulsar-standalone bin/pulsar-admin topics list public/default

# View topic stats
docker exec -it pulsar-standalone bin/pulsar-admin topics stats persistent://public/default/demo-topic
```

## Next Steps

Consider extending this POC with:
- Dead Letter Queue (DLQ) configuration
- Message batching
- Custom message routing
- Pulsar Functions integration
- Schema evolution
- Multiple consumers with different subscription types (exclusive, failover, key_shared)
- Reactive support with Spring Pulsar Reactive

