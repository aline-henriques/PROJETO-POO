#FROM ubuntu:latest AS build

#RUN apt-get update && apt-get install openjdk-17-jdk -y
#RUN apt-get install maven -y

#COPY . .

#RUN mvn clean install -DskipTests

#FROM openjdk:17-jdk-slim
#EXPOSE 8080

#COPY --from=build /target/gerenciadorweb-1.0.0.jar app.jar

#ENTRYPOINT ["java", "-jar", "app.jar"]


FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app
EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
