FROM openjdk:16-jdk-alpine
VOLUME /tmp
ADD target/backend-1.0-exec.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]