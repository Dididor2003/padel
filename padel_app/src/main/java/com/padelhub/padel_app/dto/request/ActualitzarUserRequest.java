package com.padelhub.padel_app.dto.request;

import com.padelhub.padel_app.model.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ActualitzarUserRequest {

    @NotBlank(message = "El nom és obligatori")
    private String nom;

    @Min(1) @Max(120)
    private int edat;

    private String sexe;

    private User.NivellJoc nivell;

    public ActualitzarUserRequest() {}

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getEdat() { return edat; }
    public void setEdat(int edat) { this.edat = edat; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public User.NivellJoc getNivell() { return nivell; }
    public void setNivell(User.NivellJoc nivell) { this.nivell = nivell; }
}
