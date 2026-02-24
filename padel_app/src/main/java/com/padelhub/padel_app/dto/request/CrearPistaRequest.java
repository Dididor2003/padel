package com.padelhub.padel_app.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CrearPistaRequest {

    @NotBlank(message = "El nom de la pista és obligatori")
    private String nom;

    private String tipus;

    private String descripcio;

    public CrearPistaRequest() {}

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getTipus() { return tipus; }
    public void setTipus(String tipus) { this.tipus = tipus; }

    public String getDescripcio() { return descripcio; }
    public void setDescripcio(String descripcio) { this.descripcio = descripcio; }
}
