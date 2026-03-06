package com.padelhub.padel_app.dto.request;

import jakarta.validation.constraints.NotBlank;

//  el DTO que usa l'ADMIN quan vol crear una pista nova.
public class CrearPistaRequest {

	// Atributs
    @NotBlank(message = "El nom de la pista és obligatori")
    private String nom;

    private String tipus;

    private String descripcio;

    // Constructor
    public CrearPistaRequest() {}

    // Getters & Setters
    public String getNom() { 
    	return nom; 
    }
    
    public void setNom(String nom) { 
    	this.nom = nom; 
    }

    public String getTipus() { 
    	return tipus; 
    }
    
    public void setTipus(String tipus) { 
    	this.tipus = tipus; 
    }

    public String getDescripcio() { 
    	return descripcio; 
    }
    
    public void setDescripcio(String descripcio) { 
    	this.descripcio = descripcio; 
    }
}
