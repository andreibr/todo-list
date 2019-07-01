FROM openjdk:8-jdk-alpine
COPY target/ /tmp/
COPY monitoring/extras/ /tmp/
ENTRYPOINT ["java", "-javaagent:/tmp/glowroot.jar", "-jar", "/tmp/TODO-LIST_API-1.0-SNAPSHOT.jar"]
EXPOSE 4000 8080 

