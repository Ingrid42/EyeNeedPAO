package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
@Entity
public class Reponse_choisi implements Serializable{
	
	@Id @GeneratedValue
	private Long id_reponseChoisi;

	private String value;
	private Date date_reponse;
	@OneToOne
	private Questionnaire questionnaire;
	@OneToOne
	private Reponse reponse;
 
	@OneToOne
	private Utilisateurs utilisateur;

	public Reponse_choisi(Date date_reponse, String value, Reponse reponse, Utilisateurs utilisateur, Questionnaire questionnaire) {
		super();
		this.date_reponse = date_reponse;
		this.reponse = reponse;
		this.utilisateur = utilisateur;
		this.value = value;
		this.questionnaire=questionnaire;
	}
	
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public Reponse_choisi(Date date_reponse, String value, Long reponseId, Utilisateurs utilisateur) {
		super();
		this.date_reponse = date_reponse;
		this.value = value;
	
		this.utilisateur = utilisateur;
	}
	
	public Reponse_choisi() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getId_reponseChoisi() {
		return id_reponseChoisi;
	}
	
	public void setId_reponseChoisi(Long id_reponseChoisi) {
		this.id_reponseChoisi = id_reponseChoisi;
	}
	
	public Date getDate_reponse() {
		return date_reponse;
	}
	
	public void setDate_reponse(Date date_reponse) {
		this.date_reponse = date_reponse;
	}
	
	public Reponse getReponse() {
		return reponse;
	}
	
	public void setReponse(Reponse reponse) {
		this.reponse = reponse;
	}
	
	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}


}
