# Projeto: TO-DO LIST API (API para armazenamento/leitura de tarefas)

# indice:
 - [required apps](#required-apps)
 - [to run as container](#to-run-as-container-optional)
 - [database configure](#database-configure)
 - [to compile and run (on localhost)](#to-compile-and-run-on-localhost)
 - [to do some tests](#to-do-some-tests)
 - [to running on container](#to-running-on-container)
 - [to running on kubernetes](#to-running-on-kubernetes)
 - [monitoring](#monitoring)
 - [put your packages on gcp Artifact Registry](#put-your-packages-on-gcp-artifact-registry)

# required apps:
  - maven
  - java 11
  - mysql/mariadb
  - curl (or similar)
  - jq

# to run as container (optional):
    - docker
    - minikube (or similar)

# database configure:
  - create database db_todolist;
  - use db_todolist;
  - create table task(
                 id int auto_increment primary key,
                 task_name varchar(50),
                 task_status tinyint,
                 task_description varchar(256),
                 task_init_date date,
                 task_final_date date) default charset=utf8;

# to compile and run (on localhost):
  - database must be running
  = adjuste your database connection on src/main/resorces/applicaiton.properties
  - mvn clean package
  - java -jar target/TODO-LIST_API-1.0-SNAPSHOT.jar

# to do some tests:
  - task_status: pending or completed
  - POST (insert)
    - curl -H 'Content-Type: application/json' -d '{"task_name":"task1","task_status":"pending","task_description":"task1 desc","task_init_date":"yyyy-mm-dd","task_final_date":null}' 
	   -X POST http://127.0.0.1:25000/v0/to-do/ | jq
  - GET (query)
    - curl -H 'Content-Type: application/json' -X GET http://127.0.0.1:25000/v0/to-do/ | jq
    - curl -H 'Content-Type: application/json' -X GET http://127.0.0.1:25000/v0/to-do/<b>ID</b> | jq
  - PUT (update)
    - curl -H 'Content-Type: application/json' -d '{"id":"<b>ID</b>","task_name":"task1","task_status":"pending","task_description":"task1 desc","task_init_date":"yyyy-mm-dd","task_final_date":null}' -X PUT http://127.0.0.1:25000/v0/to-do/<b>ID</b> | jq
  - DELETE
    - curl -H 'Content-Type: application/json' -X DELETE http://127.0.0.1:25000/v0/to-do/<b>ID</b> 

# to running on container:
  - build application imagem container (image exists on docker.io):
    - docker image build -f Dockerfile -t <your_repository>/todolist:1.0 . # remember, change the docker-compose to your repository
   
  - rodando como composer:
    - docker-compose up -d
    
  - create "cluster" swarm and deploy stack solution:
    - docker swarm init
    - docker service deploy -c docker-compose.yml todoCluster
    - docker service ls
  
# to running on kubernetes:
  - with minikube or similar, execute:
    - kubectl create -f kubernetes/db/permissoes.yaml
    - kubectl create -f kubernetes/db/statefulset.yaml
    - kubectl create -f kubernetes/db/servico-banco.yaml

    - kubectl create -f kubernetes/app/deployment.yaml
    - kubectl create -f kubernetes/app/servico-aplicacao.yaml

    - minikube service servico-aplicacao --url
	(use this value as addrees on your tests)

# monitoring:
  - Services:
    - /actuator		
    - /actuator/metrics 
    - /actuator/metrics/"some metric"
    - /actuator/health 
    - /actuator/info
    - /actuator/prometheus
    - port 4000: glowroot apm (https://glowroot.org/)


# put your packages on gcp Artifact Registry:
```
gcloud artifacts print-settings mvn --project=visto-dev-project --repository=pacotes-maven --location=us-east1 --json-key=/path/access_key.json
# copiar a saida e incluir os pedaços de codigo onde informado: pom.xml e ~/.m2/settings.xml
# no pom.xml tbm foi necessário incluir esse pedaço aqui: 
  <build>
    <extensions>
      <extension>
        <groupId>com.google.cloud.artifactregistry</groupId>
        <artifactId>artifactregistry-maven-wagon</artifactId>
        <version>2.1.0</version>
      </extension>
    </extensions>

# pós o build do pacote, o upload do mesmo:
mvn deploy:deploy-file -Durl=artifactregistry://us-east1-maven.pkg.dev/visto-dev-project/pacotes-maven -DpomFile=$(pwd)/pom.xml -Dfile=$(pwd)/target/todo-list_api-1.0.1.jar
#[INFO] ArtifactRegistry Maven Wagon: Retrieving credentials...
#[INFO] Trying Application Default Credentials...
#set 07, 2022 12:19:26 PM com.google.auth.oauth2.DefaultCredentialsProvider warnAboutProblematicCredentials
#[INFO] Using Application Default Credentials.
#[INFO] Using Application Default Credentials.
#[INFO] ------------------------------------------------------------------------
#[INFO] BUILD SUCCESS


gcloud artifacts packages list --repository=pacotes-maven
Listing items under project visto-dev-project, location us-east1, repository pacotes-maven.

PACKAGE                                          CREATE_TIME          UPDATE_TIME
com.google.cloud.artifactregistry:todo-list_api  2022-09-07T12:19:34  2022-09-07T12:19:34

```





