package fr.eyeneed.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Relation implements Serializable{
	
	@Id 
	private String type;

	public Relation(String type) {
		super();
		this.type = type;
	}
	public Relation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}
