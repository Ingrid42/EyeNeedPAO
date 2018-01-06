package fr.eyeneed.entities;

import java.io.Serializable;
import java.util.List;

//import org.hibernate.annotations.Cascade;
//import org.hibernate.annotations.CascadeType;

import javax.persistence.CascadeType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import org.springframework.util.concurrent.ListenableFuture;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Reponse implements Serializable, Comparable<Reponse>{

	@Id @GeneratedValue
	private Long id_reponse;

	@NotNull
	private String texte;
	

	private String ref;
	
	@ManyToOne
	private Question question;
	
	private String type; 
	/* TYPE de REPONSE POSSIBLE
	0 - aucun binaire  
	1 - texte  
	2 - range par defaut value="5" max="10" min="0" step="1" 
	3 - date 
	
	*/
	private int position;
	private boolean isCommentaire; //si un champ libre
	private String commentaire;
		
	@OneToMany(cascade = CascadeType.ALL, mappedBy="contenant", orphanRemoval = true) //fetch=FetchType.EAGER)
//	@Cascade(CascadeType.DELETE)
//a	@Fetch(FetchMode.SELECT)
	private List<Question> sousQuestions;

	public Reponse() {
		super();	
	}
	
	public Reponse(String texte, int position,boolean isCommentaire, Question question) {
		super();
		this.texte = texte;
		this.isCommentaire = isCommentaire;
		this.question = question;
		//this.type="checkbox"; //pas d'affichage dans la base de données
		this.position=position;
	}
	
	public Reponse(String texte, int position,boolean isCommentaire, Question question, String type) {
		super();
		this.texte = texte;
		this.isCommentaire = isCommentaire;
		
		this.question = question;
		this.type=type;
		this.position=position;
	}

	public Long getId_reponse() {
		return id_reponse;
	}

	public void setId_reponse(Long id_reponse) {
		this.id_reponse = id_reponse;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	@JsonIgnore
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean getIsCommentaire() {
		return isCommentaire;
	}
	
	public void setIsCommentaire(boolean isCommentaire) {
		this.isCommentaire=isCommentaire;
	}
	
	public void setCommentaire(boolean isCommentaire) {
		this.isCommentaire = isCommentaire;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public List<Question> getSousQuestions() {
		return sousQuestions;
	}

	public void setSousQuestions(List<Question> sousQuestions) {
		this.sousQuestions = sousQuestions;
	}
	
	public boolean aSousSousQuestion() {
		for(int i=0; i<sousQuestions.size();i++){
			Question question = sousQuestions.get(i);
			for(int j=0; j<question.getReponses().size();j++){
				if(!question.getReponses().get(j).getSousQuestions().isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int compareTo(Reponse o) {
        return this.getPosition()-o.getPosition();
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

}
