FROM openjdk:23-jdk-slim
WORKDIR /app

COPY build/libs/approval-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
CMD ["java", "-jar", "app.jar"]