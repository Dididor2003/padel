package com.padelhub.padel_app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pistes")
public class Pista {

    @Id
    private String id;

    private String nom;           // Ex: "Pista 1", "Pista Central"

    private String tipus;         // Ex: "INDOOR", "OUTDOOR"

    private boolean disponible;

    private String descripcio;

    public Pista() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getTipus() { return tipus; }
    public void setTipus(String tipus) { this.tipus = tipus; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public String getDescripcio() { return descripcio; }
    public void setDescripcio(String descripcio) { this.descripcio = descripcio; }
}
