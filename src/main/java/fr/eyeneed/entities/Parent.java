package fr.eyeneed.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class Parent implements Serializable{
	@Id @GeneratedValue
	private Long id_parent;
	@OneToOne
	private Utilisateurs parent1;
	@OneToOne
	private Utilisateurs parent2;
	@OneToOne
	private Relation relation;
	
	public Parent(Utilisateurs parent1, Utilisateurs parent2, Relation relation) {
		super();
		this.parent1 = parent1;
		this.parent2 = parent2;
		this.relation = relation;
	}
	public Parent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Utilisateurs getParent1() {
		return parent1;
	}
	public void setParent1(Utilisateurs parent1) {
		this.parent1 = parent1;
	}
	public Utilisateurs getParent2() {
		return parent2;
	}
	public void setParent2(Utilisateurs parent2) {
		this.parent2 = parent2;
	}
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}
	public Long getId_parent() {
		return id_parent;
	}
	public void setId_parent(Long id_parent) {
		this.id_parent = id_parent;
	}

	
}
