package br.com.abr.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import br.com.abr.entity.Task;

@RestResource(exported = false)
public interface ITaskRepository extends JpaRepository<Task, Integer> {

}
