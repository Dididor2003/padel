package com.padelhub.padel_app.dto.response;

// resposta que es retorna al client després d'un login exitós
public class JwtResponse {

    // Atributs
    private String token;
    private String email;
    private String nom;
    private String rol;

    // Constructors
    public JwtResponse() {}

    // constructor amb paràmetres per crear la resposta directament des de l'AuthController
    public JwtResponse(String token, String email, String nom, String rol) {
        this.token = token;
        this.email = email;
        this.nom = nom;
        this.rol = rol;
    }

    // Getters & Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}