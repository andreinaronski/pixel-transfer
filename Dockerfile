FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /
COPY . .
RUN mvn install -DskipTests

FROM eclipse-temurin:17-alpine
WORKDIR /
COPY --from=builder /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
