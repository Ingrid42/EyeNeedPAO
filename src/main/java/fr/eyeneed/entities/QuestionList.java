package fr.eyeneed.entities;

import java.util.ArrayList;
import java.util.List;

/* object permettant d'enrgistrer les questions/reponses sur la page Form_Admin_Question */
public class QuestionList {
	
	private List<Question> questions= new ArrayList<Question>();
	private List<Question> newQuestions = new ArrayList<Question>();
	private List<Reponse> reponses = new ArrayList<Reponse>();
	private List<Reponse> newReponses = new ArrayList<Reponse>();
	private List<RelationQR> relationQRs = new ArrayList<RelationQR>();
	private Module module;
	
	public QuestionList(){}
	
	public QuestionList(List<Question> questions, Module module,List<Reponse> reponses){
		this.questions=questions;
		this.module=module;
		this.reponses=reponses;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public List<Question> getNewQuestions() {
		return newQuestions;
	}

	public void setNewQuestions(List<Question> questions) {
		this.newQuestions = questions;
	}
	
	public Module getModule(){
		return this.module;
	}
	
	public void setModule(Module module){
		this.module=module;
	}
	
	public List<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponse> reponses) {
		this.reponses = reponses;
	}

	public List<Reponse> getNewReponses() {
		return newReponses;
	}

	public void setNewReponses(List<Reponse> newReponses) {
		this.newReponses = newReponses;
	}

	public List<RelationQR> getRelationQRs() {
		return relationQRs;
	}

	public void setRelationQR(List<RelationQR> relationQRs) {
		this.relationQRs = relationQRs;
	}

}