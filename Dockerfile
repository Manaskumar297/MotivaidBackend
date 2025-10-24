# Step 1: Use Maven to build the jar
FROM maven:3.9.11-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy all source code
COPY src ./src

# Build the application (skip tests)
RUN mvn clean package -DskipTests

# Step 2: Use a lightweight JDK image to run the jar
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/motivaid-0.0.1-SNAPSHOT.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Command to run the jar
ENTRYPOINT ["java","-jar","app.jar"]
