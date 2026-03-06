package com.padelhub.padel_app.dto.response;

import java.time.LocalDateTime;

// resposta per als resultats de partides
public class ResultatResponse {

	// Atributs
    private String id;
    private String reservaId;
    private String guanyadorId;
    private String nomGuanyador;
    private String perdedorId;
    private String nomPerdedor;
    private String marcador;
    private LocalDateTime dataPartida;
    private String notes;

    // Constructor
    public ResultatResponse() {}

    // Getters & Setters
    public String getId() { 
    	return id; 
    }
    
    public void setId(String id) { 
    	this.id = id;
    }

    public String getReservaId() { 
    	return reservaId; 
    }
    
    public void setReservaId(String reservaId) {
    	this.reservaId = reservaId; 
    }

    public String getGuanyadorId() {
    	return guanyadorId; 
    }
    
    public void setGuanyadorId(String guanyadorId) { 
    	this.guanyadorId = guanyadorId; 
    }

    public String getNomGuanyador() { 
    	return nomGuanyador; 
    }
    
    public void setNomGuanyador(String nomGuanyador) { 
    	this.nomGuanyador = nomGuanyador; 
    }

    public String getPerdedorId() { 
    	return perdedorId;
    }
    
    public void setPerdedorId(String perdedorId) { 
    	this.perdedorId = perdedorId; 
    }

    public String getNomPerdedor() { 
    	return nomPerdedor; 
    }
    
    public void setNomPerdedor(String nomPerdedor) { 
    	this.nomPerdedor = nomPerdedor; 
    }

    public String getMarcador() { 
    	return marcador; 
    }
    
    public void setMarcador(String marcador) { 
    	this.marcador = marcador; 
    }

    public LocalDateTime getDataPartida() { 
    	return dataPartida; 
    }
    
    public void setDataPartida(LocalDateTime dataPartida) { 
    	this.dataPartida = dataPartida; 
    }

    public String getNotes() { 
    	return notes; 
    }
    
    public void setNotes(String notes) { 
    	this.notes = notes; 
    }
}
