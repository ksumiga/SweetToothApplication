
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR .. 
COPY . .      
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim
COPY --from=build /com/example/SweetTooth-0.0.1-SNAPSHOT.jar /com/example/SweetTooth.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/com/example/SweetTooth.jar"]