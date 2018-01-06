package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.List;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

//import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.junit.Ignore;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class Question implements Serializable, Comparable<Question>{
	@Id 
	@GeneratedValue
	private Long id_question;
	private String texte;
	
	@OneToOne
	private Module module;
	private boolean multiple; //si plusieurs réponses
	private int position;

	
	@OneToMany(mappedBy="question",fetch=FetchType.EAGER)
	@Cascade(CascadeType.DELETE) 
	@Fetch(FetchMode.SELECT)
	private List<Reponse> reponses;
	
	@ManyToOne
	private Reponse contenant; //la réponse qui engendre la question
	
	public Question(String texte, Module module, int position) {
		super();
		this.texte = texte;
		this.module = module;
		this.position = position;
		this.multiple =false;
		
	}
	public Question(String texte, Module module, int position, boolean multiple) {
		super();
		this.texte = texte;
		this.module = module;
		this.position = position;
		this.multiple =multiple;
		
	}
	public boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	public Question() {
		super();
	}
	
	public Long getId_question() {
		return id_question;
	}
	public void setId_question(Long id_question) {
		this.id_question = id_question;
	}
	public String getTexte() {
		return texte;
	}
	public void setTexte(String texte) {
		this.texte = texte;
	}
	@JsonIgnore
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public List<Reponse> getReponses() {
		return reponses;
	}
	public void setReponses(List<Reponse> reponses) {
		this.reponses = reponses;
		
	}
	public void addReponse(Reponse reponse){
		this.reponses.add(reponse);
	}
	@JsonIgnore
	public Reponse getContenant() {
		return contenant;
	}
	public void setContenant(Reponse contenant) {
		this.contenant = contenant;
	}
	
	@Override
	public int compareTo(Question o) {
        return this.getPosition()-o.getPosition();
	}
}

