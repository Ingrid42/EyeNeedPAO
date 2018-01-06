package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Log implements Serializable{
	@Id @GeneratedValue
private Long id_logger;
private Date date;
private String info; //
@ManyToOne
private Utilisateurs utilisateur;

public Log(Date date, String info, Utilisateurs utilisateur) {
	super();
	this.date = date;
	this.info = info;
	this.utilisateur = utilisateur;
}

public Log() {
	super();
	// TODO Auto-generated constructor stub
}

public Long getId_logger() {
	return id_logger;
}
public void setId_logger(Long id_logger) {
	this.id_logger = id_logger;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public String getInfo() {
	return info;
}
public void setInfo(String info) {
	this.info = info;
}
public Utilisateurs getUtilisateur() {
	return utilisateur;
}
public void setUtilisateur(Utilisateurs utilisateur) {
	this.utilisateur = utilisateur;
}

}
