# Notification Service

Short description

This service listens for payment and delivery lifecycle events and logs (simulates) user notifications.

Key info
- Java: 21
- Spring Boot: 3.5.7
- Kafka topics used: `payment-completed`, `order-ready-for-delivery`, `order-delivered`
- No HTTP controllers in this service (event-driven)

Prerequisites
- Java 21
- Maven / `mvnw.cmd`
- Kafka cluster

Configuration
- `src/main/resources/env.properties` contains bootstrap server and group id.

Build and run

```bash
cd notification-service-main
mvnw.cmd -DskipTests package
java -jar target/notification-service-0.0.1-SNAPSHOT.jar
```

Kafka

- Consumes: `payment-completed`, `order-ready-for-delivery`, `order-delivered`

Troubleshooting

- Check logs for notifications; confirm Kafka connectivity.
