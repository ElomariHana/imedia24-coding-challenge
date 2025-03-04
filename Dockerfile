# Use Maven with JDK 21 for the build stage
FROM maven:3.9.6-eclipse-temurin-21 as build

WORKDIR /app

# Copy the POM file and download dependencies first (improves caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Use a smaller JDK image for runtime
FROM openjdk:21-jdk-slim as runtime

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/shop-0.0.1-SNAPSHOT.jar shop-0.0.1-SNAPSHOT.jar

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app/shop-0.0.1-SNAPSHOT.jar"]
