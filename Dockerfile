FROM openjdk:17.0.2-jdk-slim-buster
LABEL maintainer="https://github.com/staimov"
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/employee-directory-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/employee-directory-1.0.0.jar"]
