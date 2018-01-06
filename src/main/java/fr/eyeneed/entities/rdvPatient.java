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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="rdvPatient")
public class rdvPatient implements Serializable {

	@Id @GeneratedValue
	private Long id;

	@Email
	private String mail;

	@NotNull
	private String nom;
	
	@NotNull
	private String prenom;
	
	private String tel;
	
	private String typeRdv;
	
	private String Status = "En attente"; //demandé appelé mailé traité
	
	private Date date;
	
	private String commentaire;
	
	private String specialiste;
	
	private String orientation = "";
	
	
	@NotNull
	private Long codePostal;
	
	public rdvPatient() {
		super();
	}
	
	public rdvPatient(String mail, String nom, String prenom, String tel, String typeRdv) {
		super();
		this.setMail(mail);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setTel(tel);
		this.setTypeRdv(typeRdv);
	}
	public rdvPatient(String mail, String nom, String prenom, String tel, String typeRdv,Long codePostal,String status, Date date, String specialiste, String orientation) {
		super();
		this.setMail(mail);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setTel(tel);
		this.setTypeRdv(typeRdv);
		this.Status="En attente";
		this.codePostal=codePostal;
		this.date=date;
		this.commentaire="<br/> rendez-vous demandé le " + date;
		this.setSpecialiste(specialiste);
		this.setOrientation(orientation);

	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTypeRdv() {
		return typeRdv;
	}

	public void setTypeRdv(String typeRdv) {
		this.typeRdv = typeRdv;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Long getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(Long codePostal) {
		this.codePostal = codePostal;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getSpecialiste() {
		return specialiste;
	}

	public void setSpecialiste(String specialiste) {
		this.specialiste = specialiste;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

//	public long getOrdonnance() {
//		return ordonnance;
//	}
//
//	public void setOrdonnance(long ordonnance) {
//		this.ordonnance = ordonnance;
//	}

}
