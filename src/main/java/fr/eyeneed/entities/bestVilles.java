package fr.eyeneed.entities;

import java.math.BigInteger;

public class bestVilles {
BigInteger codePostal;
BigInteger count;
public BigInteger getCodePostal() {
	return codePostal;
}
public void setCodePostal(BigInteger codePostal) {
	this.codePostal = codePostal;
}
public BigInteger getCount() {
	return count;
}
public void setCount(BigInteger count) {
	this.count = count;
}
public bestVilles() {
	super();
	// TODO Auto-generated constructor stub
}
public bestVilles(BigInteger codePostal, BigInteger count) {
	super();
	this.codePostal = codePostal;
	this.count = count;
}

}
