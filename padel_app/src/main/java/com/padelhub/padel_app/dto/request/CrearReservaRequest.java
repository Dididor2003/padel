package com.padelhub.padel_app.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

// el DTO per crear una reserva, que suporta dos tipus de reserva diferents.
public class CrearReservaRequest {

	// Atributs
    @NotBlank(message = "La pistaId és obligatòria")
    private String pistaId;

    @NotNull(message = "La data i hora és obligatòria")
    private LocalDateTime dataHora;

    @Min(60) @Max(120)
    private int duracioMinuts = 60;

    private String jugador2Id; // null si vol assignació automàtica

    private boolean assignacioAutomatica;

    // Constructor
    public CrearReservaRequest() {}

    // Getters & Setters
    public String getPistaId() { 
    	return pistaId; 
    }
    
    public void setPistaId(String pistaId) { 
    	this.pistaId = pistaId; 
    }

    public LocalDateTime getDataHora() { 
    	return dataHora; 
    }
    
    public void setDataHora(LocalDateTime dataHora) { 
    	this.dataHora = dataHora; 
    }

    public int getDuracioMinuts() { 
    	return duracioMinuts; 
    }
    
    public void setDuracioMinuts(int duracioMinuts) { 
    	this.duracioMinuts = duracioMinuts; 
    }

    public String getJugador2Id() { 
    	return jugador2Id; 
    }
    
    public void setJugador2Id(String jugador2Id) { 
    	this.jugador2Id = jugador2Id; 
    }

    public boolean isAssignacioAutomatica() { 
    	return assignacioAutomatica; 
    }
    
    public void setAssignacioAutomatica(boolean assignacioAutomatica) { 
    	this.assignacioAutomatica = assignacioAutomatica; 
    }
}
