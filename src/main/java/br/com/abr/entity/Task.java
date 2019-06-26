package br.com.abr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private String task_name;
    private String task_status;
    private String task_description;
    private Date task_init_date;
    private Date task_final_date;

    public int getId() {
		return id;
	}
    
    public void setId(Integer id) {
    	this.id = id;
    }
    
    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public Date getTask_init_date() {
        return task_init_date;
    }

    public void setTask_init_date(Date task_init_date) {
        this.task_init_date = task_init_date;
    }

    public Date getTask_final_date() {
        return task_final_date;
    }

    public void setTask_final_date(Date task_final_date) {
        this.task_final_date = task_final_date;
    }

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

}
