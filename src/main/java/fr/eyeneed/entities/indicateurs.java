package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
public class indicateurs implements Serializable{
	@Id 
private String date;
private Integer visites; //nombre de visite sur index
private Integer connectes; //moyenne de connexion sur la journée
private Integer questionnaires; // nombre de questionnaires remplies
private Integer examens;
private Integer consultations;
private Integer fondOeil;
private Integer lunettes;
private Integer lentilles;
private Integer controle;
private Integer chirurgie;

public indicateurs() {
	super();

}

public indicateurs(String date) {
	//initialise l'indicateur
	super();
	this.date = date;
	this.visites=0;
	this.connectes=0;
	this.questionnaires=0 ;
	this.examens=0;
	this.consultations=0;
	this.fondOeil=0;
	this.lunettes=0;
	this.setLentilles(0);
	this.controle=0;
	this.chirurgie=0;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public Integer getVisites() {
	return visites;
}
public void setVisites(Integer visites) {
	this.visites = visites;
}
public Integer getConnectes() {
	return connectes;
}
public void setConnectes(Integer connectes) {
	this.connectes = connectes;
}
public Integer getQuestionnaires() {
	return questionnaires;
}
public void setQuestionnaires(Integer questionnaires) {
	this.questionnaires = questionnaires;
}
public Integer getExamens() {
	return examens;
}
public void setExamens(Integer examens) {
	this.examens = examens;
}
public Integer getConsultations() {
	return consultations;
}
public void setConsultations(Integer consultations) {
	this.consultations = consultations;
}
public Integer getFondOeil() {
	return fondOeil;
}
public void setFondOeil(Integer fondOeil) {
	this.fondOeil = fondOeil;
}

public Integer getLunettes() {
	return lunettes;
}
public void setLunettes(Integer lunettes) {
	this.lunettes = lunettes;
}
public Integer getControle() {
	return controle;
}
public void setControle(Integer controle) {
	this.controle = controle;
}
public Integer getChirurgie() {
	return chirurgie;
}
public void setChirurgie(Integer chirurgie) {
	this.chirurgie = chirurgie;
}

public Integer getLentilles() {
	return lentilles;
}

public void setLentilles(Integer lentilles) {
	this.lentilles = lentilles;
}

}
