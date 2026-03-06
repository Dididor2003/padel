package com.padelhub.padel_app.dto.response;

// representa les dades d'una pista que l'API retorna al client
public class PistaResponse {

	// Atributs
    private String id;
    private String nom;
    private String tipus;
    private boolean disponible;
    private String descripcio;

    // Constructor
    public PistaResponse() {}

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
