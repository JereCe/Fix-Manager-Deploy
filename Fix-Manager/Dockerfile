FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/fix-manager-0.0.1.jar
COPY ${JAR_FILE} app_fixmanager.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app_fixmanager.jar"]