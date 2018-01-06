package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Module implements Serializable, Comparable<Module>{

	@Id @GeneratedValue
	private Long id_module;
	
	private String nom;
	
	private int position;
	
	@OneToMany(mappedBy="module",cascade=CascadeType.REMOVE)
	private List<Question> questions;

	public Module(String nom, int position, List<Question> questions) {
		super();
		this.nom = nom;
		this.position = position;
		this.questions = questions;
	}
	public Module(String nom, int position) {
		super();
		this.nom = nom;
		this.position = position;
		this.questions = null;
	}
	public Module(String nom) {
		super();
		this.nom = nom;
		this.position = 0;
		this.questions = null;
	}
	public Module() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public Long getId_module() {
		return id_module;
	}
	public void setId_module(Long id_module) {
		this.id_module = id_module;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	@JsonIgnore
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public int compareTo(Module o) {
        return this.getPosition()-o.getPosition();
	}

}
