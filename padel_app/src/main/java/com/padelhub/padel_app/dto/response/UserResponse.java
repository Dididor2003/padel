package com.padelhub.padel_app.dto.response;

import com.padelhub.padel_app.model.User;

public class UserResponse {

    private String id;
    private String nom;
    private String email;
    private int edat;
    private String sexe;
    private User.NivellJoc nivell;
    private String rol;
    private int totalPartides;
    private int partidesGuanyades;

    public UserResponse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getEdat() { return edat; }
    public void setEdat(int edat) { this.edat = edat; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public User.NivellJoc getNivell() { return nivell; }
    public void setNivell(User.NivellJoc nivell) { this.nivell = nivell; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public int getTotalPartides() { return totalPartides; }
    public void setTotalPartides(int totalPartides) { this.totalPartides = totalPartides; }

    public int getPartidesGuanyades() { return partidesGuanyades; }
    public void setPartidesGuanyades(int partidesGuanyades) { this.partidesGuanyades = partidesGuanyades; }
}
