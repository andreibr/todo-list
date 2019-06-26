package br.com.abr.controller;


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
@RequestMapping(path = "/to-do")
public class TaskController {

    @Autowired
    private ITaskRepository repository;

    @GetMapping
    public Iterable<Task> findAll() {
        return repository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Task find(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }

    @PostMapping(consumes = "application/json")
    public Task create(@RequestBody Task task) {
        return repository.save(task);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Integer id) {
        repository.delete(id);
    }

    @PutMapping(path = "/{id}")
    public Task update(@PathVariable("id") Integer id, @RequestBody Task task) throws BadHttpRequest {
        if (repository.exists(id)) {
            return repository.save(task);
        } else {
            throw new BadHttpRequest();
        }
    }

}