
FROM maven:3.8.5-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/SweetTooth-0.0.1-SNAPSHOT.jar /app/SweetTooth.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/SweetTooth.jar"]