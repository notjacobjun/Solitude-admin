#FROM maven:3.6.3-jdk-8 AS build
#COPY src /usr/src/app/src
#COPY pom.xml /usr/src/app
#WORKDIR /usr/src/app
#RUN mvn package

FROM openjdk:8-jdk-alpine
COPY target/Solitude-0.0.1-SNAPSHOT.jar /usr/src/app/app.jar
COPY solitude-credentials.json solitude-credentials.json
COPY src/main/resources/credentials.json src/main/resources/credentials.json
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/usr/src/app/app.jar"]