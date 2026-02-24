package com.padelhub.padel_app.dto.response;

import com.padelhub.padel_app.model.Reserva;

import java.time.LocalDateTime;

public class ReservaResponse {

    private String id;
    private String pistaId;
    private String nomPista;
    private LocalDateTime dataHora;
    private int duracioMinuts;
    private String jugador1Id;
    private String nomJugador1;
    private String jugador2Id;
    private String nomJugador2;
    private Reserva.EstatReserva estat;
    private LocalDateTime dataCreacio;

    public ReservaResponse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPistaId() { return pistaId; }
    public void setPistaId(String pistaId) { this.pistaId = pistaId; }

    public String getNomPista() { return nomPista; }
    public void setNomPista(String nomPista) { this.nomPista = nomPista; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public int getDuracioMinuts() { return duracioMinuts; }
    public void setDuracioMinuts(int duracioMinuts) { this.duracioMinuts = duracioMinuts; }

    public String getJugador1Id() { return jugador1Id; }
    public void setJugador1Id(String jugador1Id) { this.jugador1Id = jugador1Id; }

    public String getNomJugador1() { return nomJugador1; }
    public void setNomJugador1(String nomJugador1) { this.nomJugador1 = nomJugador1; }

    public String getJugador2Id() { return jugador2Id; }
    public void setJugador2Id(String jugador2Id) { this.jugador2Id = jugador2Id; }

    public String getNomJugador2() { return nomJugador2; }
    public void setNomJugador2(String nomJugador2) { this.nomJugador2 = nomJugador2; }

    public Reserva.EstatReserva getEstat() { return estat; }
    public void setEstat(Reserva.EstatReserva estat) { this.estat = estat; }

    public LocalDateTime getDataCreacio() { return dataCreacio; }
    public void setDataCreacio(LocalDateTime dataCreacio) { this.dataCreacio = dataCreacio; }
}
