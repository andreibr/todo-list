#FROM adoptopenjdk/openjdk11:jre
#WORKDIR /app
#USER 1000
#COPY ./target/agenda-*.jar ./agenda.jar
#EXPOSE 25000
#ENTRYPOINT ["java", "-jar", "./agenda.jar"]


############# versao velha - apenas localhost
FROM alpine AS agent

WORKDIR /app
RUN apk update; apk add curl
RUN curl -LO https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar


FROM maven AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests

FROM azul/zulu-openjdk-alpine:11.0.20.1-11.66.19-jre
ARG DD_GIT_REPOSITORY_URL
ARG DD_GIT_COMMIT_SHA
ENV DD_GIT_REPOSITORY_URL=${DD_GIT_REPOSITORY_URL} 
ENV DD_GIT_COMMIT_SHA=${DD_GIT_COMMIT_SHA}
#COPY --from=agent /app/*.jar /tmp/
COPY --from=build /app/target/*.jar /tmp/
#VAR JAVA_OPTS="-javaagent:/tmp/opentelemetry-javaagent.jar"
ENTRYPOINT ["java", "-jar", "/tmp/agenda-1.0.13.jar"]
EXPOSE 25000

# "-Dotel.service.name=otel-agenda", "-Ddeployment.environment=dev-abr", "-Dservice.version=v1.0.13", "-Dotel.resource.attributes=service.name=otel-agenda,deployment.environment=dev-abr,service.version=v1.0.13"
