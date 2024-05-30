# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application's jar to the container
COPY target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
