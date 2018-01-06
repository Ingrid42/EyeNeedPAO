package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class Medecin implements Serializable{
	@Id @GeneratedValue
	private Long id_medecin;
	private String specialite;
	@OneToMany
	private List<Utilisateurs>  utilisateurs;
	
	public Long getId_medecin() {
		return id_medecin;
	}
	public void setId_medecin(Long id_medecin) {
		this.id_medecin = id_medecin;
	}
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	public List<Utilisateurs> getUtilisateurs() {
		return utilisateurs;
	}
	public void setUtilisateurs(List<Utilisateurs> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}
	public Medecin(String specialite, List<Utilisateurs> utilisateurs) {
		super();
		this.specialite = specialite;
		this.utilisateurs = utilisateurs;
	}
	public Medecin() {
		super();
		// TODO Auto-generated constructor stub
	} 
	
}
