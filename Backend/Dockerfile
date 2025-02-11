FROM openjdk:17-jdk

WORKDIR /app

COPY target/engineeringProject.jar .

COPY src/main/resources ./src/main/resources

EXPOSE 8080

CMD ["java", "-jar", "engineeringProject.jar"]

