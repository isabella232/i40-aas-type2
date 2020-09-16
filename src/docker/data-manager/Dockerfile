#FROM maven:3.6.1-jdk-8-alpine AS builder
FROM openjdk:8-jdk-alpine as build

WORKDIR /cmd/data-manager

COPY cmd/data-manager/mvnw /cmd/data-manager
COPY cmd/data-manager/.mvn/ /cmd/data-manager/.mvn/
COPY cmd/data-manager/pom.xml /cmd/data-manager
#COPY cmd/data-manager/mvnw.cmd /cmd/data-manager
#COPY cmd/data-manager/.classpath /cmd/data-manager
#COPY cmd/data-manager/.factorypath /cmd/data-manager
#COPY cmd/data-manager/.project /cmd/data-manager

COPY cmd/data-manager/src/main/resources/ /cmd/data-manager/src/main/resources/

#RUN mvn dependency:copy-dependencies

COPY cmd/data-manager/src/main/java/ /cmd/data-manager/src/main/java/

#RUN mvn install -DskipTests


RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

###################################
FROM openjdk:8-jre-alpine3.9 AS prod

RUN adduser -D aasuser

WORKDIR /cmd/data-manager

# COPY --from=builder /cmd/data-manager/target/*.jar /cmd/data-manager/dataManagerApp.jar
#COPY --from=builder /cmd/data-manager/target/ /cmd/data-manager

ARG DEPENDENCY=/cmd/data-manager/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /cmd/data-manager/app/lib
COPY --from=build ${DEPENDENCY}/META-INF /cmd/data-manager/app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /cmd/data-manager/app

USER aasuser
EXPOSE 2001

#ENTRYPOINT ["java","-jar","./data-manager-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["java","-cp","app:app/lib/*","com.sap.i40aas.datamanager.DataManagerApplication"]
