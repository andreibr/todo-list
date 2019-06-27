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
  - to do; 
