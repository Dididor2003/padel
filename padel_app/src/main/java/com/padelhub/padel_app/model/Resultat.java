package com.padelhub.padel_app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "resultats")
public class Resultat {

    @Id
    private String id;

    private String reservaId;

    private String guanyadorId;

    private String perdedorId;

    private String marcador;

    private LocalDateTime dataPartida;

    private String notes;

    public Resultat() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getReservaId() { return reservaId; }
    public void setReservaId(String reservaId) { this.reservaId = reservaId; }

    public String getGuanyadorId() { return guanyadorId; }
    public void setGuanyadorId(String guanyadorId) { this.guanyadorId = guanyadorId; }

    public String getPerdedorId() { return perdedorId; }
    public void setPerdedorId(String perdedorId) { this.perdedorId = perdedorId; }

    public String getMarcador() { return marcador; }
    public void setMarcador(String marcador) { this.marcador = marcador; }

    public LocalDateTime getDataPartida() { return dataPartida; }
    public void setDataPartida(LocalDateTime dataPartida) { this.dataPartida = dataPartida; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
