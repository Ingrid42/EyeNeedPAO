package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class Questionnaire implements Serializable{
	@Id @GeneratedValue
	private Long id_questionnaire;
	private Date date_validee;
	
	private Long duree;
	@ManyToOne
	private Utilisateurs utilisateur;
	
	
	public Long getId_questionnaire() {
		return id_questionnaire;
	}
	public void setId_questionnaire(Long id_questionnaire) {
		this.id_questionnaire = id_questionnaire;
	}
	public Date getDate_validee() {
		return date_validee;
	}
	public void setDate_validee(Date date_validee) {
		this.date_validee = date_validee;
	}
	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	
	public Questionnaire(Date date_validee, Utilisateurs utilisateur, Long duree) {
		super();
		this.date_validee = date_validee;
		this.utilisateur = utilisateur;
		this.duree=duree;
	}
	public Long getDuree() {
		return duree;
	}
	public void setDuree(Long duree) {
		this.duree = duree;
	}
	public Questionnaire() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
