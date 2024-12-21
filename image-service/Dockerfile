FROM openjdk:17-jdk-alpine
EXPOSE 8084
ADD target/*.jar app.jar
COPY target/classes/static /target/classes/static/
ENTRYPOINT ["java","-jar","/app.jar"]
