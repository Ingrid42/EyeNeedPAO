package fr.eyeneed.entities;

import java.util.List;

public class Form {
	//class a envoyé au questionnaire pour récuperer les réponses
	private List<Reponse_choisi> reponses;
	
	public Form() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Form(List<Reponse_choisi> reponses) {
		super();
		this.reponses = reponses;
	}

	public List<Reponse_choisi> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponse_choisi> reponses) {
		this.reponses = reponses;
	}


}
