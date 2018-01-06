package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "synthese")

public class Synthese implements Serializable {	
	
	@Id @GeneratedValue
	private int id;
	
	private String prenom;
	
	private String nom;
	
	private String login;
	
	private int age;
	
	private String anneeOrdonnance;

	private String anteOphtalmo;

	private String anteChir;

	private String anteFamiliaux;
	
	private String diabete;


	
	public Synthese() {
		super();
	}

	public Synthese(String prenom, String nom, String login, String anteOphtalmo, String anteChir, String anteFamiliaux, String diabete, int age, String anneeOrdonnance) {
		super();
		this.setPrenom(prenom);
		this.setNom(nom);
		this.setLogin(login);
		this.setAnteOphtalmo(anteOphtalmo);
		this.setAnteChir(anteChir);
		this.setAnteFamiliaux(anteFamiliaux);
		this.setDiabete(diabete);
		this.setAge(age);
		this.setAnneeOrdonnance(anneeOrdonnance);
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAnteOphtalmo() {
		return anteOphtalmo;
	}

	public void setAnteOphtalmo(String anteOphtalmo) {
		this.anteOphtalmo = anteOphtalmo;
	}

	public String getAnteChir() {
		return anteChir;
	}

	public void setAnteChir(String anteChir) {
		this.anteChir = anteChir;
	}

	public String getAnteFamiliaux() {
		return anteFamiliaux;
	}

	public void setAnteFamiliaux(String anteFamiliaux) {
		this.anteFamiliaux = anteFamiliaux;
	}

	public String getDiabete() {
		return diabete;
	}

	public void setDiabete(String diabete) {
		this.diabete = diabete;
	}


	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAnneeOrdonnance() {
		return anneeOrdonnance;
	}

	public void setAnneeOrdonnance(String anneeOrdonnance) {
		this.anneeOrdonnance = anneeOrdonnance;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	

	}