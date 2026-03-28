FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/scala-2.13/analytics-service.jar app.jar

CMD ["java", "-jar", "app.jar"]