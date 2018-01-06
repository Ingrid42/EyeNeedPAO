package fr.eyeneed.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Etablissement implements Serializable{
	@Id @GeneratedValue
	private Long id_etablissement;
	private String nom;
	private String adresse;
	private int codepostal;
	private String ville;
	private String tel;
	
	public Long getId_etablissement() {
		return id_etablissement;
	}
	public void setId_etablissement(Long id_etablissement) {
		this.id_etablissement = id_etablissement;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getCodepostal() {
		return codepostal;
	}
	public void setCodepostal(int codepostal) {
		this.codepostal = codepostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Etablissement(String nom, String adresse, int codepostal, String ville, String tel) {
		super();
		this.nom = nom;
		this.adresse = adresse;
		this.codepostal = codepostal;
		this.ville = ville;
		this.tel = tel;
	}
	public Etablissement() {
		super();
		// TODO Auto-generated constructor stub
	}
}
