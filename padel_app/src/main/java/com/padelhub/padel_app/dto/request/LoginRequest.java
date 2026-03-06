package com.padelhub.padel_app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// representa les dades que envia el client quan vol fer login
public class LoginRequest {

	// Atributs
    @NotBlank(message = "L'email és obligatori")
    @Email(message = "Format d'email invàlid")
    private String email;

    @NotBlank(message = "La contrasenya és obligatòria")
    private String password;

    // Constructor
    public LoginRequest() {}

    // Getters & Setters
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
}
