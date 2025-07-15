FROM openjdk:21-jdk-slim

ARG JAR_FILE=./build/libs/capybara-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

WORKDIR /app
COPY .env .env

EXPOSE 443

ENTRYPOINT ["java","-jar","/app.jar"]