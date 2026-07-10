FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /home/app
COPY --from=builder /app/target/*.jar /home/app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]