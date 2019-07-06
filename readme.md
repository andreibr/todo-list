# Projeto: TODO-LIST API (API para armazenamento/leitura de tarefas)

# required apps:
  - maven
  - java 8
  - mysql
  - curl (or similar)
  - jq

# to running as container (optional):
    - docker
    - minikube (or similar)

# database configure:
  - create database db_todolist
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
	   -X POST http://127.0.0.1:8080/v0/to-do/ | jq
  - GET (query)
    - curl -H 'Content-Type: application/json' -X GET http://127.0.0.1:8080/v0/to-do/ | jq
    - curl -H 'Content-Type: application/json' -X GET http://127.0.0.1:8080/v0/to-do/<b>ID</b> | jq
  - PUT (update)
    - curl -H 'Content-Type: application/json' -d '{"id":"<b>ID</b>","task_name":"task1","task_status":"pending","task_description":"task1 desc","task_init_date":"yyyy-mm-dd","task_final_date":null}' -X PUT http://127.0.0.1:8080/v0/to-do/<b>ID</b> | jq
  - DELETE
    - curl -H 'Content-Type: application/json' -X DELETE http://127.0.0.1:8080/v0/to-do/<b>ID</b> 

# to running on container:
  - build application imagem container (image exists on docker.io):
    - docker image build -f Dockerfile -t andreibr/todolist:1.0 .
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

