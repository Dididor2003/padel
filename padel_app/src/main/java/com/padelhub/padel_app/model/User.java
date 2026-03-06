package com.padelhub.padel_app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
public class User {

	// Atributs
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String nom;

    private int edat;

    private String sexe;

    private NivellJoc nivell;

    private Role rol;

    private LocalDateTime dataRegistre;

    private boolean esperantParella;

    private int totalPartides;

    private int partidesGuanyades;

    public enum NivellJoc {
        BASIC, MITJA, ALT
    }

    public enum Role {
        USER, ADMIN
    }

    // Constructor
    public User() {}
    
    // Getters & Setters
    public String getId() { 
    	return id; 
    }
    
    public void setId(String id) { 
    	this.id = id; 
    }

    public String getEmail() { 
    	return email; 
    }
    
    public void setEmail(String email) { 
    	this.email = email; 
    }

    public String getPassword() { 
    	return password; 
    }
    
    public void setPassword(String password) { 
    	this.password = password; 
    }

    public String getNom() { 
    	return nom; 
    }
    
    public void setNom(String nom) { 
    	this.nom = nom; 
    }

    public int getEdat() { 
    	return edat; 
    }
    
    public void setEdat(int edat) { 
    	this.edat = edat; 
    }

    public String getSexe() { 
    	return sexe; 
    }
    
    public void setSexe(String sexe) { 
    	this.sexe = sexe;
    }

    public NivellJoc getNivell() { 
    	return nivell; 
    }

    public void setNivell(NivellJoc nivell) { 
    	this.nivell = nivell; 
    }

    public Role getRol() { 
    	return rol; 
    }
    
    public void setRol(Role rol) { 
    	this.rol = rol; 
    }

    public LocalDateTime getDataRegistre() { 
    	return dataRegistre; 
    }
    
    public void setDataRegistre(LocalDateTime dataRegistre) { 
    	this.dataRegistre = dataRegistre; 
    }

    public boolean isEsperantParella() { 
    	return esperantParella; 
    }
    
    public void setEsperantParella(boolean esperantParella) { 
    	this.esperantParella = esperantParella; 
    }

    public int getTotalPartides() { 
    	return totalPartides; 
    }
    
    public void setTotalPartides(int totalPartides) { 
    	this.totalPartides = totalPartides; 
    }

    public int getPartidesGuanyades() { 
    	return partidesGuanyades; 
    }
    
    public void setPartidesGuanyades(int partidesGuanyades) { 
    	this.partidesGuanyades = partidesGuanyades; 
    }
}
