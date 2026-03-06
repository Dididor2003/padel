package com.padelhub.padel_app.dto.request;

import com.padelhub.padel_app.model.User;
import jakarta.validation.constraints.*;

//  conté tots els camps que ha d'enviar el client per crear un compte nou.
public class RegisterRequest {

	// Atributs
    @NotBlank(message = "El nom és obligatori")
    private String nom;

    @NotBlank(message = "L'email és obligatori")
    @Email(message = "Format d'email invàlid")
    private String email;

    @NotBlank(message = "La contrasenya és obligatòria")
    @Size(min = 6, message = "La contrasenya ha de tenir mínim 6 caràcters")
    private String password;

    @Min(value = 1) @Max(value = 120)
    private int edat;

    @NotBlank(message = "El sexe és obligatori")
    private String sexe;

    private User.NivellJoc nivell;

    // Constructor
    public RegisterRequest() {}

    // Getters & Setters
    public String getNom() { 
    	return nom; 
    }
    
    public void setNom(String nom) { 
    	this.nom = nom; 
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

    public User.NivellJoc getNivell() { 
    	return nivell; 
    }
    
    public void setNivell(User.NivellJoc nivell) { 
    	this.nivell = nivell; 
    }
}
