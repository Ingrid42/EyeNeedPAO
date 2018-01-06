package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.Calendar;
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
import java.util.logging.*;
@Entity
@Table(name = "utilisateurs")

public class Utilisateurs implements Serializable {
	@Id
	private String login;
	@Email
	private String mail;
	private Date derniereConnexion;
	private boolean accesQuestionnaire;//peut répondre au questionnaire
	@NotNull
	private String nom;
	@NotNull
	private String prenom;
	private int genre;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull
	private Date naissance;
	@NotNull
	private Long codePostal;
	@NotNull
	private String password;
	private boolean active;
	private String tel;
	@OneToMany(mappedBy = "utilisateur")
	private List<Reponse_choisi> reponses;
	@ManyToMany
	private List<Role> role;
	@OneToMany
	private List<Parent> parents;
	public int age(){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(naissance);  
		int AnneeNaissance = cal.get(Calendar.YEAR);
		
		Date actualDate = new Date(System.currentTimeMillis());
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(actualDate);  
		int AnneeActuelle = cal2.get(Calendar.YEAR);
		
		int age = AnneeActuelle - AnneeNaissance;
		System.out.println("age de l'utilisateur =" +age);
		return age;
	}
	public boolean isAdmin(){
		boolean r=false;
		
		for (Role role : this.role) {
			System.out.println("l'utilisateur à le role "+ role.getNom());
			if(role.getNom().equals("ADMIN")){
			
			r= true;
			break;
			}
		}
		return r;
	}
	public boolean isSecretaire(){
		boolean r=false;
		
		for (Role role : this.role) {
			System.out.println("l'utilisateur à le role "+ role.getNom());
			if(role.getNom().equals("SECRETAIRE")){
			
			r= true;
			break;
			}
		}
		return r;
	}
	public Utilisateurs() {
		super();

	}

	public Utilisateurs(String login,String mail, String nom, String prenom, int genre, Date naissance, Long codePostal,
			String password, String tel, List<Role> role) {
		super();
		this.login=login;
		this.mail = mail;
		this.nom = nom;
		this.prenom = prenom;
		this.genre = genre;
		this.naissance = naissance;
		this.codePostal = codePostal;
		this.password = password;
		// this.active = false;//désactive le compte lors de la création
		this.active = true;// dans un premier temps désactive la validation par
							// token
		this.tel = tel;
		this.role = role;
	}
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getNaissance() {
		return naissance;
	}

	public void setNaissance(Date naissance) {
		this.naissance = naissance;
	}
	public int getGenre() {
		return genre;
	}

	public void setGenre(int genre) {
		this.genre = genre;
	}
	public Long getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(Long codePostal) {
		this.codePostal = codePostal;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@JsonIgnore
	public List<Reponse_choisi> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponse_choisi> reponses) {
		this.reponses = reponses;
	}

	@JsonIgnore
	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	@JsonIgnore
	public List<Parent> getParents() {
		return parents;
	}

	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}
	public boolean isAccesQuestionnaire() {
		return accesQuestionnaire;
	}

	public void setAccesQuestionnaire(boolean accesQuestionnaire) {
		this.accesQuestionnaire = accesQuestionnaire;
	}

	public Date getDerniereConnexion() {
		return derniereConnexion;
	}

	public void setDerniereConnexion(Date derniereConnexion) {
		this.derniereConnexion = derniereConnexion;
	}

	
}