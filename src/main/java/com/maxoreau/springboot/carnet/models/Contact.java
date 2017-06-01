package com.maxoreau.springboot.carnet.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
	private int contactId;
	private String prenom;
	private String nom;
	private String numero;
	
	public Contact() {}

	public Contact(String prenom, String nom, String numero) {
		this.prenom = prenom;
		this.nom = nom;
		this.numero = numero;
	}

	public Contact(int contactId, String prenom, String nom, String numero) {
		this.contactId = contactId;
		this.prenom = prenom;
		this.nom = nom;
		this.numero = numero;
	}
	
	public Contact(Contact c) {
		this.contactId = c.contactId;
		this.prenom = c.prenom;
		this.nom = c.nom;
		this.numero = c.numero;
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
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	@Override
	public String toString() {
		return "Contact [nom=" + nom + ", prenom=" + prenom + ", numero=" + numero + ", contactId=" + contactId + "]";
	}

}
