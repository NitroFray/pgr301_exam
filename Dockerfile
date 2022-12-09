FROM maven:3.8.6-jdk-11-slim AS build
COPY src /src
COPY pom.xml /pom.xml
RUN mvn -f /pom.xml clean package

FROM adoptopenjdk/openjdk11
COPY --from=build target/onlinestore-0.0.1-SNAPSHOT.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/application.jar"]