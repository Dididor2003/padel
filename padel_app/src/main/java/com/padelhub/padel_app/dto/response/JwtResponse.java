package com.padelhub.padel_app.dto.response;

public class JwtResponse {

    private String token;
    private String email;
    private String nom;
    private String rol;

    public JwtResponse() {}

    public JwtResponse(String token, String email, String nom, String rol) {
        this.token = token;
        this.email = email;
        this.nom = nom;
        this.rol = rol;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
