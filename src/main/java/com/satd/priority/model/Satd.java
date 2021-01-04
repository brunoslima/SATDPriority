package com.satd.priority.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name="SATD")
public class Satd {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(columnDefinition="text", length=10485760)
	private String description;
	private String resource;
	private String path;
	private String line;
	private String type;
	private double priority;
	private String paid;

	@Transient
    private ArrayList<Context> contextInstances;
	
	public Satd(String description, String resource, String path, String line, String type) {
		
		this.description = description;
		this.resource = resource;
		this.path = path + "/" + resource;
		this.line = line;
		this.type = type;
		this.priority = 0.0;
		this.paid = "NOT";
		this.contextInstances = new ArrayList<Context>();
	}

	public Satd(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPriority() {
		return priority;
	}

	public void setPriority(double priority) {
		this.priority = priority;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}
	
	public ArrayList<Context> getContextInstances() {
		return contextInstances;
	}

	public void setContextInstances(ArrayList<Context> contextInstances) {
		this.contextInstances = contextInstances;
	}

	public String show() {
		
		return(this.description + " | " + 
		       this.path + " | " + 
		       this.resource + " | " + 
		       this.line + " | " + 
		       this.type);
	}

}
