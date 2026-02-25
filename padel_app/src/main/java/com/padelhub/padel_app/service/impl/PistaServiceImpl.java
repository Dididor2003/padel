package com.padelhub.padel_app.service.impl;

import com.padelhub.padel_app.dto.request.CrearPistaRequest;
import com.padelhub.padel_app.dto.response.PistaResponse;
import com.padelhub.padel_app.exception.BadRequestException;
import com.padelhub.padel_app.exception.RecursoNoEncontradoException;
import com.padelhub.padel_app.model.Pista;
import com.padelhub.padel_app.model.Reserva;
import com.padelhub.padel_app.repository.PistaRepository;
import com.padelhub.padel_app.repository.ReservaRepository;
import com.padelhub.padel_app.service.PistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PistaServiceImpl implements PistaService {

    @Autowired
    private PistaRepository pistaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public PistaResponse crear(CrearPistaRequest request) {
        if (pistaRepository.existsByNom(request.getNom())) {
            throw new BadRequestException("Ja existeix una pista amb el nom: " + request.getNom());
        }

        Pista pista = new Pista();
        pista.setNom(request.getNom());
        pista.setTipus(request.getTipus());
        pista.setDescripcio(request.getDescripcio());
        pista.setDisponible(true);

        return toResponse(pistaRepository.save(pista));
    }

    @Override
    public List<PistaResponse> llistarTotes() {
        return pistaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PistaResponse> llistarDisponibles() {
        return pistaRepository.findByDisponibleTrue().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PistaResponse getById(String id) {
        Pista pista = pistaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pista no trobada"));
        return toResponse(pista);
    }

    @Override
    public PistaResponse actualitzar(String id, CrearPistaRequest request) {
        Pista pista = pistaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pista no trobada"));
        pista.setNom(request.getNom());
        pista.setTipus(request.getTipus());
        pista.setDescripcio(request.getDescripcio());
        return toResponse(pistaRepository.save(pista));
    }

    @Override
    public void eliminar(String id) {
        if (!pistaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Pista no trobada");
        }
        pistaRepository.deleteById(id);
    }

    @Override
    public List<PistaResponse> getPistesDisponiblesPerHorari(LocalDateTime dataHora, int duracioMinuts) {
        return pistaRepository.findByDisponibleTrue().stream()
                .filter(pista -> {
                    LocalDateTime fi = dataHora.plusMinutes(duracioMinuts);
                    List<Reserva> conflictes = reservaRepository
                            .findByPistaIdAndDataHoraBetween(pista.getId(), dataHora.minusMinutes(duracioMinuts), fi)
                            .stream()
                            .filter(r -> r.getEstat() != Reserva.EstatReserva.CANCEL_LADA)
                            .collect(Collectors.toList());
                    return conflictes.isEmpty();
                })
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PistaResponse toResponse(Pista pista) {
        PistaResponse dto = new PistaResponse();
        dto.setId(pista.getId());
        dto.setNom(pista.getNom());
        dto.setTipus(pista.getTipus());
        dto.setDisponible(pista.isDisponible());
        dto.setDescripcio(pista.getDescripcio());
        return dto;
    }
}
