package fr.eyeneed.entities;

import java.io.Serializable;

public class RelationQR implements Serializable{
	
	private String question; 
	private String reponse;
	private String relation;
	
	public RelationQR(){
	
	}
	
	public RelationQR(String question, String reponse, String relation){
		this.question=question;
		this.reponse=reponse;
		this.relation=relation;	
	}
	
	public String getRelation() {
		return relation;
	}
	
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public String getReponse() {
		return reponse;
	}
	
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
}