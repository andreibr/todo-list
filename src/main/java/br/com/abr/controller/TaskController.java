package br.com.abr.controller;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;

import br.com.abr.entity.Task;
import br.com.abr.repository.ITaskRepository;
import javassist.tools.web.BadHttpRequest;

@RestController
@ResponseStatus
@RequestMapping(path = "/v0/to-do")
public class TaskController {

	@Autowired
	private Environment env;

	private List<Task> tasks;
	private Logger logger = Logger.getLogger(TaskController.class.getName());

	@Autowired
	private ITaskRepository repository;

	// how to parse response body, HTTP status messages
    //@GetMapping
	//public Iterable<Task> findAll() {
    //   	tasks = repository.findAll();
    //    return tasks;
	//}
	@GetMapping
	public ResponseEntity<Iterable<Task>> findAll() {
		getWaether();
	   	tasks = repository.findAll();
		if (tasks.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}
	
	private void getWaether() {
		String uri =  "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m";
		String resul = new RestTemplate().getForObject(uri, String.class);
		logger.info("INFO: " + resul);
	}	

	@GetMapping(path = "/version")
	public String version() {
		Dictionary<String, String> versao = new Hashtable<String, String>();
		versao.put("versao", env.getProperty("app.version"));
		logger.info("INFO: " + versao.toString());
		Gson gson = new Gson();
		return gson.toJson(versao);		
	}

	// @GetMapping(path = "/{id}")
	// public ResponseEntity<Iterable<Task>> find(@PathVariable("id") Integer id) {
    //    	tasks = repository.findById(id);
	// 	if (tasks.isEmpty()) {
	// 		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	// 	} 
	// 	return new ResponseEntity<>(tasks, HttpStatus.OK);
	// }

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Iterable<Task>> create(@RequestBody Task task) {

       	task = repository.save(task);
		if (tasks.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} 
		return  findAll();
	}

	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable("id") Integer id) {

       	repository.deleteById(id);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Iterable<Task>> update(@PathVariable("id") Integer id, @RequestBody Task task) throws BadHttpRequest {
		if (repository.existsById(id)) {
        	task = repository.save(task);
			return findAll();
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
