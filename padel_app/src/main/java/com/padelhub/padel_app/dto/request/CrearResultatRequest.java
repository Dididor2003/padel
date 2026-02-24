package com.padelhub.padel_app.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class CrearResultatRequest {

    @NotBlank(message = "La reservaId és obligatòria")
    private String reservaId;

    @NotBlank(message = "El guanyadorId és obligatori")
    private String guanyadorId;

    @NotBlank(message = "El perdedorId és obligatori")
    private String perdedorId;

    private String marcador;

    private LocalDateTime dataPartida;

    private String notes;

    public CrearResultatRequest() {}

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
