FROM maven:3.8.5-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18.0.1-jdk-slim
COPY --from=build /target/AttackWeb-1.0-SNAPSHOT.jar app/AttackWeb.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app/AttackWeb.jar"]