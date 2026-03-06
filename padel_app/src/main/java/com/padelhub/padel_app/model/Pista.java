package com.padelhub.padel_app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pistes")
public class Pista {

	// Atributs
    @Id
    private String id;

    private String nom;

    private String tipus;

    private boolean disponible;

    private String descripcio;

    // Constructor
    public Pista() {}

    // Getters & Setters
    public String getId() { 
    	return id; 
    }
    
    public void setId(String id) { 
    	this.id = id;
    }

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

    public boolean isDisponible() { 
    	return disponible; 
    }
    
    public void setDisponible(boolean disponible) { 
    	this.disponible = disponible; 
    }

    public String getDescripcio() { 
    	return descripcio; 
    }
    
    public void setDescripcio(String descripcio) { 
    	this.descripcio = descripcio; 
    }
    
}
