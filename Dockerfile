# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim as build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the source code into the container
COPY pom.xml .
COPY src ./src

# Build the project using Maven
RUN mvn clean install -DskipTests

# Expose the port on which the app will run
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/your-app.jar"]
