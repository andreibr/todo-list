package br.com.abr.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.abr.entity.Task;
import br.com.abr.repository.ITaskRepository;
import javassist.tools.web.BadHttpRequest;

@RestController
@RequestMapping(path = "/v0/to-do")
public class TaskController {
	
	private List<Task> tasks;
	private Optional<Task> task;

	@Autowired
	private ITaskRepository repository;

	@GetMapping
	public Iterable<Task> findAll() {

       	tasks = repository.findAll();
        return tasks;
	}

	@GetMapping(path = "/{id}")
	public Optional<Task> find(@PathVariable("id") Integer id) {

       	task = repository.findById(id);
		return task;
	}

	@PostMapping(consumes = "application/json")
	public Task create(@RequestBody Task task) {

       	task = repository.save(task);
		return task;
	}

	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable("id") Integer id) {

       	repository.deleteById(id);
	}

	@PutMapping(path = "/{id}")
	public Task update(@PathVariable("id") Integer id, @RequestBody Task task) throws BadHttpRequest {
		if (repository.existsById(id)) {
        	task = repository.save(task);
			return task;
		} else {
			throw new BadHttpRequest();
		}
	}	
}