FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar dataManagerApp.jar
ENTRYPOINT ["java","-jar","/dataManagerApp.jar"]