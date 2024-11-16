FROM openjdk:21-slim
WORKDIR /app
COPY /target/demo-0.0.1-SNAPSHOT.jar pidlbe.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "pidlbe.jar"]


