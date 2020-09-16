FROM maven:3.6.1-jdk-8-alpine AS builder

WORKDIR /cmd/data-manager

COPY cmd/data-manager/mvnw /cmd/data-manager
COPY cmd/data-manager/pom.xml /cmd/data-manager
COPY cmd/data-manager/mvnw.cmd /cmd/data-manager
COPY cmd/data-manager/.classpath /cmd/data-manager
COPY cmd/data-manager/.factorypath /cmd/data-manager
COPY cmd/data-manager/.project /cmd/data-manager

COPY cmd/data-manager/src/main/resources/ /cmd/data-manager/src/main/resources/

# RUN mvn dependency:copy-dependencies

COPY cmd/data-manager/src/main/java/ /cmd/data-manager/src/main/java/

RUN mvn install:install-file -Dfile=src/main/resources/i40-aas-objects-kotlin-jvm-1.0-SNAPSHOT.jar -DgroupId=i40.aas -DartifactId=objects-jvm -Dversion=0.0.1-master-SNAPSHOT -Dpackaging=jar


RUN mvn install -DskipTests

###################################
FROM openjdk:8-jre-alpine3.9 AS prod

RUN adduser -D aasuser

WORKDIR /cmd/data-manager

COPY --from=builder /cmd/data-manager/target/*.jar /cmd/data-manager/dataManagerApp.jar

USER aasuser
EXPOSE 2001

ENTRYPOINT ["java","-jar","./dataManagerApp.jar"]
