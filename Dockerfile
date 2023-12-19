FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /resource-server

COPY --from=build target/*.jar resource-server.jar
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "resource-server.jar"]