FROM maven:3.6.1-jdk-8-alpine AS builder

WORKDIR /cmd/data-manager

COPY cmd/data-manager/pom.xml /cmd/data-manager
COPY cmd/data-manager/src/ /cmd/data-manager/src/

RUN mvn install:install-file -Dfile=src/main/resources/i40-aas-objects-kotlin-jvm-1.0-SNAPSHOT.jar -DgroupId=i40.aas -DartifactId=objects-jvm -Dversion=0.0.1-master-SNAPSHOT -Dpackaging=jar
RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

###################################
FROM openjdk:8-jre-alpine3.9 AS prod

RUN adduser -D aasuser

WORKDIR /cmd/data-manager

COPY --from=builder /cmd/data-manager/target/dependency/BOOT-INF/classes /cmd/data-manager/app
COPY --from=builder /cmd/data-manager/target/dependency/BOOT-INF/lib /cmd/data-manager/app/lib
COPY --from=builder /cmd/data-manager/target/dependency/META-INF /cmd/data-manager/app/META-INF

USER aasuser
EXPOSE 2001

ENTRYPOINT ["java","-cp","app:app/lib/*","com.sap.i40aas.datamanager.DataManagerApplication"]
