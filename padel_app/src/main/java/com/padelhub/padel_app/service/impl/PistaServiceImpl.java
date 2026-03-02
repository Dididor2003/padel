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
        try {
            if (pistaRepository.existsByNom(request.getNom())) {
                throw new BadRequestException("Ja existeix una pista amb el nom: " + request.getNom());
            }

            Pista pista = new Pista();
            pista.setNom(request.getNom());
            pista.setTipus(request.getTipus());
            pista.setDescripcio(request.getDescripcio());
            pista.setDisponible(true);

            return toResponse(pistaRepository.save(pista));

        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperat en crear la pista: " + e.getMessage(), e);
        }
    }

    @Override
    public List<PistaResponse> llistarTotes() {
        try {
            return pistaRepository.findAll().stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir la llista de pistes: " + e.getMessage(), e);
        }
    }

    @Override
    public List<PistaResponse> llistarDisponibles() {
        try {
            return pistaRepository.findByDisponibleTrue().stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir les pistes disponibles: " + e.getMessage(), e);
        }
    }

    @Override
    public PistaResponse getById(String id) {
        try {
            Pista pista = pistaRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Pista no trobada"));
            return toResponse(pista);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir la pista amb id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public PistaResponse actualitzar(String id, CrearPistaRequest request) {
        try {
            Pista pista = pistaRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Pista no trobada"));
            pista.setNom(request.getNom());
            pista.setTipus(request.getTipus());
            pista.setDescripcio(request.getDescripcio());
            return toResponse(pistaRepository.save(pista));
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error en actualitzar la pista: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(String id) {
        try {
            if (!pistaRepository.existsById(id)) {
                throw new RecursoNoEncontradoException("Pista no trobada");
            }
            pistaRepository.deleteById(id);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error en eliminar la pista: " + e.getMessage(), e);
        }
    }

    @Override
    public List<PistaResponse> getPistesDisponiblesPerHorari(LocalDateTime dataHora, int duracioMinuts) {
        try {
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
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir les pistes disponibles per l'horari indicat: " + e.getMessage(), e);
        }
    }

    public PistaResponse toResponse(Pista pista) {
        try {
            PistaResponse dto = new PistaResponse();
            dto.setId(pista.getId());
            dto.setNom(pista.getNom());
            dto.setTipus(pista.getTipus());
            dto.setDisponible(pista.isDisponible());
            dto.setDescripcio(pista.getDescripcio());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error en convertir la pista a resposta: " + e.getMessage(), e);
        }
    }
}