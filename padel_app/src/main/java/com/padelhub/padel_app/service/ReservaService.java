package com.padelhub.padel_app.service;

import com.padelhub.padel_app.dto.request.CrearReservaRequest;
import com.padelhub.padel_app.dto.response.ReservaResponse;

import java.util.List;

public interface ReservaService {

    ReservaResponse crearReserva(CrearReservaRequest request, String emailJugador1);

    List<ReservaResponse> getHistorialUsuari(String userId);

    List<ReservaResponse> getTotes();

    ReservaResponse getById(String id);

    ReservaResponse cancellar(String id, String emailUsuari);
}
