package com.padelhub.padel_app.service;

import com.padelhub.padel_app.dto.request.CrearPistaRequest;
import com.padelhub.padel_app.dto.response.PistaResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PistaService {

    PistaResponse crear(CrearPistaRequest request);

    List<PistaResponse> llistarTotes();

    List<PistaResponse> llistarDisponibles();

    PistaResponse getById(String id);

    PistaResponse actualitzar(String id, CrearPistaRequest request);

    void eliminar(String id);

    List<PistaResponse> getPistesDisponiblesPerHorari(LocalDateTime dataHora, int duracioMinuts);
}
