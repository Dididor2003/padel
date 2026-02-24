package com.padelhub.padel_app.dto.response;

public class EstadistiquesResponse {

    private String userId;
    private String nom;
    private int totalPartides;
    private int partidesGuanyades;
    private int partidesPerdes;
    private double percentatgeVictories;
    private int totalReserves;

    public EstadistiquesResponse() {}

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getTotalPartides() { return totalPartides; }
    public void setTotalPartides(int totalPartides) { this.totalPartides = totalPartides; }

    public int getPartidesGuanyades() { return partidesGuanyades; }
    public void setPartidesGuanyades(int partidesGuanyades) { this.partidesGuanyades = partidesGuanyades; }

    public int getPartidesPerdes() { return partidesPerdes; }
    public void setPartidesPerdes(int partidesPerdes) { this.partidesPerdes = partidesPerdes; }

    public double getPercentatgeVictories() { return percentatgeVictories; }
    public void setPercentatgeVictories(double percentatgeVictories) { this.percentatgeVictories = percentatgeVictories; }

    public int getTotalReserves() { return totalReserves; }
    public void setTotalReserves(int totalReserves) { this.totalReserves = totalReserves; }
}
