FROM openjdk:23-jdk-slim
WORKDIR /app

COPY build/libs/event-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8089
CMD ["java", "-jar", "app.jar"]
