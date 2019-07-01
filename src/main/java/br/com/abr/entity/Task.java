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
		if (this.task_status.contentEquals("0")) {
			this.task_status = "pending";
		} else if (this.task_status.contentEquals("1")) {
			this.task_status = "completed";
		}

		return this.task_status;
	}

	public void setTask_status(String task_status) {
		if (task_status.contentEquals("pending")) {
			this.task_status = "0";
		} else if (task_status.contentEquals("completed")) {
			this.task_status = "1";
		} else {
			throw new RuntimeException("Parametro com valor invalido: pending or completed são valores validos");
		}

	}

	public String getTask_description() {
		return task_description;
	}

	public void setTask_description(String task_description) {
		this.task_description = task_description;
	}

	public String getTask_init_date() {
		return task_init_date.toString().substring(0, 10);
	}

	public void setTask_init_date(Date task_init_date) {
		this.task_init_date = task_init_date;
	}

	public String getTask_final_date() {
		String dataFinal = "";
		if(this.task_status.contentEquals("completed"))
			dataFinal = task_final_date.toString().substring(0, 10);
		
		return dataFinal; 
	}

	public void setTask_final_date(Date task_final_date) {

		if (this.task_status.contentEquals("1")) {
			
			Date df = task_final_date;
			
			if (df.before(this.task_init_date)) {
				throw new RuntimeException("Data final deve ser igual ou maior que a inicial");
			}
		}
		
		this.task_final_date = task_final_date;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

}
