FROM openjdk:21

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the working directory in the container
COPY target/bankmicroservice-0.0.1-SNAPSHOT.jar /app/bankmicroservice.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/bankmicroservice.jar"]
