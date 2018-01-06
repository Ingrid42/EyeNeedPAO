package fr.eyeneed.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
	@Entity
	public class Motif implements Serializable{
	@Id 
	private String libelle;

	public Motif() {
		super();
		
	}
	
	public Motif(String libelle) {
		super();
		this.libelle = libelle;
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


}
