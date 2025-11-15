FROM ubuntu:latest AS build

RUN apt-get update && apt-get install openjdk-17-jdk -y
RUN apt-get install maven -y

COPY . .

RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
EXPOSE 8080

COPY --from=build /target/gerenciadorweb-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]


