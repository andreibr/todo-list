FROM maven AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests
#RUN mvn deploy:deploy-file -Durl=artifactregistry://us-east1-maven.pkg.dev/visto-dev-project/pacotes-maven


FROM adoptopenjdk/openjdk11:jdk-11.0.7_10-alpine
COPY --from=build /app/target/ /tmp/
COPY --from=build /app/monitoring/extras/ /tmp/
ENTRYPOINT ["java", "-javaagent:/tmp/glowroot.jar", "-jar", "/tmp/todo-list_api-1.0.1.jar"]
EXPOSE 4000 25000

