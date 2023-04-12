FROM maven:3.6.0-jdk-11-slim AS build

WORKDIR /home/app
COPY . ./
RUN mvn -f /home/app/pom.xml clean package
FROM openjdk:11
ENV JAR_FILE /home/app/axity.task-api/target/*.jar
COPY --from=build ${JAR_FILE} /app.jar

ENV TZ America/Mexico_City
EXPOSE 9090

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=local" ]