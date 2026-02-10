package com.padelhub.padel_app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reserves")
public class Reserva {

    @Id
    private String id;

    private String pistaId;       // Referència a Pista

    private LocalDateTime dataHora;

    private int duracioMinuts;

    private String jugador1Id;

    private String jugador2Id;

    private EstatReserva estat;

    private LocalDateTime dataCreacio;

    public enum EstatReserva {
        PENDENT,
        CONFIRMADA,
        ESPERANT_PARELLA,
        COMPLETADA,
        CANCEL_LADA
    }

    public Reserva() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPistaId() { return pistaId; }
    public void setPistaId(String pistaId) { this.pistaId = pistaId; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public int getDuracioMinuts() { return duracioMinuts; }
    public void setDuracioMinuts(int duracioMinuts) { this.duracioMinuts = duracioMinuts; }

    public String getJugador1Id() { return jugador1Id; }
    public void setJugador1Id(String jugador1Id) { this.jugador1Id = jugador1Id; }

    public String getJugador2Id() { return jugador2Id; }
    public void setJugador2Id(String jugador2Id) { this.jugador2Id = jugador2Id; }

    public EstatReserva getEstat() { return estat; }
    public void setEstat(EstatReserva estat) { this.estat = estat; }

    public LocalDateTime getDataCreacio() { return dataCreacio; }
    public void setDataCreacio(LocalDateTime dataCreacio) { this.dataCreacio = dataCreacio; }
}
