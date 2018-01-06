package fr.eyeneed.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Role implements Serializable{

	@Id 
	private String nom;
	private String description;
	public Role() {
		super();
	}
	public Role(String nom, String description) {
		super();
		this.nom = nom;
		this.description = description;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
