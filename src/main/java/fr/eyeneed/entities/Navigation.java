package fr.eyeneed.entities;

public class Navigation {
private String url;
private String nom;
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public Navigation() {
	super();
	// TODO Auto-generated constructor stub
}
public Navigation(String url, String nom) {
	super();
	this.url = url;
	this.nom = nom;
}

}
