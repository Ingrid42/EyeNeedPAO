package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="categorie")
public class Categorie implements Serializable {

	@Id @GeneratedValue
	private Long id;

	private int NumCategorie;

	private String rdv;

	private String utilisateur;

	
	public Categorie() {
		super();
	}
	
	public Categorie(int NumCategorie, String rdv, String utilisateur) {
		super();
		this.setNumCategorie(NumCategorie);
		this.setRdv(rdv);
		this.utilisateur = utilisateur;
	}
	
	public Categorie(int NumCategorie, String utilisateur) {
		super();
		this.setNumCategorie(NumCategorie);
		this.rdv = "";
		this.utilisateur = utilisateur;
	}
	

	public int getNumCategorie() {
		return NumCategorie;
	}

	public void setNumCategorie(int numCategorie) {
		NumCategorie = numCategorie;
	}

	public String getRdv() {
		return rdv;
	}

	public void setRdv(String rdv) {
		this.rdv = rdv;
	}
	
	public String getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}


}
