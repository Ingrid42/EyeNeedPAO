package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
@Entity
public class Consultation implements Serializable {
	@Id @GeneratedValue
	private Long id_consultation;
	
	private Date rdv;
	
	private Utilisateurs patient;
	
	private Medecin medecin;
	
	private Etablissement etablissement;
	
	@OneToMany
	private List<Motif>  motifs;
	
	public Consultation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Consultation(Date rdv, Utilisateurs patient, Medecin medecin, Etablissement etablissement,
			List<Motif> motifs) {
		super();
		this.rdv = rdv;
		this.patient = patient;
		this.medecin = medecin;
		this.etablissement = etablissement;
		this.motifs = motifs;
	}
	public Long getId_consultation() {
		return id_consultation;
	}
	public void setId_consultation(Long id_consultation) {
		this.id_consultation = id_consultation;
	}
	public Date getRdv() {
		return rdv;
	}
	public void setRdv(Date rdv) {
		this.rdv = rdv;
	}
	public Utilisateurs getPatient() {
		return patient;
	}
	public void setPatient(Utilisateurs patient) {
		this.patient = patient;
	}
	public Medecin getMedecin() {
		return medecin;
	}
	public void setMedecin(Medecin medecin) {
		this.medecin = medecin;
	}
	public Etablissement getEtablissement() {
		return etablissement;
	}
	public void setEtablissement(Etablissement etablissement) {
		this.etablissement = etablissement;
	}
	public List<Motif> getMotifs() {
		return motifs;
	}
	public void setMotifs(List<Motif> motifs) {
		this.motifs = motifs;
	}
	
	
}
