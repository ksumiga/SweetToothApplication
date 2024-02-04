FROM maven:3.8.5-openjdk-17 AS build
COPY ..
RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim AS build
COPY --from=com/example/SweetTooth-0.0.1-SNAPSHOT.jar SweetTooth.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SweetTooth.jar"]
