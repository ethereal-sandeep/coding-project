# Use a builder image to build the JAR
FROM maven:3.8.1-openjdk-17-slim AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use the actual image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/calendly-0.0.1-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "./app.jar"]
