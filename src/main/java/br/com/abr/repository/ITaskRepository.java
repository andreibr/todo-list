package br.com.abr.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.abr.entity.Task;

public interface ITaskRepository extends JpaRepository<Task, Integer> {

}
