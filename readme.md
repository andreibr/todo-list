# Projeto: TODO-LIST API (API para armazenamento/leitura de tarefas)

# requirede apps:
  - maven
  - java 8
  - mysql
  - curl (or similar)
  - jq

# running as container (optional):
    - docker
    - minikube (or similar)

# configure database:
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
  - mvn clean package
  - java -jar target/TODO-LIST_API-1.0-SNAPSHOT.jar

# to some tests:
  - POST (insert)
    - curl -H 'Content-Type: application/json' -d '{"task_name":"task1","task_status":"0","task_description":"task1 desc","task_init_date":"yyyy-mm-dd","task_final_date":null}' 
	   -X POST http://localhost:8080/to-do/ | jq
  - GET (query)
    - curl -H 'Content-Type: application/json' -X GET http://localhost:8080/to-do/ | jq
    - curl -H 'Content-Type: application/json' -X GET http://localhost:8080/to-do/<b>ID</b> | jq
  - PUT (update)
    - curl -H 'Content-Type: application/json' -d '{"id":"<b>ID</b>","task_name":"task1","task_status":"0","task_description":"task1 desc","task_init_date":"yyyy-mm-dd","task_final_date":null}' 
	   -X PUT http://localhost:8080/to-do/<b>ID</b> | jq
  - DELETE
    - curl -H 'Content-Type: application/json' -X DELETE http://localhost:8080/to-do/<b>ID</b> 

# to running on container:
  - build application imagem container:
    - docker image build -f Dockerfile -t teste/todolist:1.0 .
  - create "cluster" swarm and deploy stack solution:
    - docker swarm init
    - docker service deploy -c docker-compose.yml todoCluster
    - docker service ls
  - OBS.: to access application use: http://127.0.0.1 + port

# to running on kubernetes:
  - to do;

# monitoring:
  - Services:
    - port 8080/dump
    - port 8080/metrics 
    - port 8080/health 
    - port 8080/info
    - port 4000: glowroot apm (https://glowroot.org/)
    - port 9090/prometheus monitoring solution (https://prometheus.io/)	# to do

