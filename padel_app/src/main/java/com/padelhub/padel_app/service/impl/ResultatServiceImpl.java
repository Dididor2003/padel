package com.padelhub.padel_app.service.impl;

import com.padelhub.padel_app.dto.request.CrearResultatRequest;
import com.padelhub.padel_app.dto.response.ResultatResponse;
import com.padelhub.padel_app.exception.BadRequestException;
import com.padelhub.padel_app.exception.RecursoNoEncontradoException;
import com.padelhub.padel_app.model.Reserva;
import com.padelhub.padel_app.model.Resultat;
import com.padelhub.padel_app.repository.ReservaRepository;
import com.padelhub.padel_app.repository.ResultatRepository;
import com.padelhub.padel_app.repository.UserRepository;
import com.padelhub.padel_app.service.ResultatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultatServiceImpl implements ResultatService {

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResultatResponse registrarResultat(CrearResultatRequest request) {
        try {
            Reserva reserva = reservaRepository.findById(request.getReservaId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no trobada"));

            if (reserva.getEstat() != Reserva.EstatReserva.CONFIRMADA) {
                throw new BadRequestException("La reserva ha d'estar confirmada per registrar el resultat");
            }

            if (resultatRepository.findByReservaId(request.getReservaId()).isPresent()) {
                throw new BadRequestException("Ja existeix un resultat per aquesta reserva");
            }

            Resultat resultat = new Resultat();
            resultat.setReservaId(request.getReservaId());
            resultat.setGuanyadorId(request.getGuanyadorId());
            resultat.setPerdedorId(request.getPerdedorId());
            resultat.setMarcador(request.getMarcador());
            resultat.setDataPartida(request.getDataPartida() != null ? request.getDataPartida() : LocalDateTime.now());
            resultat.setNotes(request.getNotes());

            Resultat saved = resultatRepository.save(resultat);

            userRepository.findById(request.getGuanyadorId()).ifPresent(u -> {
                u.setTotalPartides(u.getTotalPartides() + 1);
                u.setPartidesGuanyades(u.getPartidesGuanyades() + 1);
                userRepository.save(u);
            });
            userRepository.findById(request.getPerdedorId()).ifPresent(u -> {
                u.setTotalPartides(u.getTotalPartides() + 1);
                userRepository.save(u);
            });

            reserva.setEstat(Reserva.EstatReserva.COMPLETADA);
            reservaRepository.save(reserva);

            return toResponse(saved);

        } catch (RecursoNoEncontradoException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperat en registrar el resultat: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResultatResponse> getHistorialUsuari(String userId) {
        try {
            return resultatRepository.findByGuanyadorIdOrPerdedorId(userId, userId)
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir l'historial de resultats de l'usuari: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ResultatResponse> getTots() {
        try {
            return resultatRepository.findAll().stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir tots els resultats: " + e.getMessage(), e);
        }
    }

    @Override
    public ResultatResponse getById(String id) {
        try {
            Resultat r = resultatRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Resultat no trobat"));
            return toResponse(r);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir el resultat amb id " + id + ": " + e.getMessage(), e);
        }
    }

    private ResultatResponse toResponse(Resultat r) {
        try {
            ResultatResponse dto = new ResultatResponse();
            dto.setId(r.getId());
            dto.setReservaId(r.getReservaId());
            dto.setGuanyadorId(r.getGuanyadorId());
            dto.setPerdedorId(r.getPerdedorId());
            dto.setMarcador(r.getMarcador());
            dto.setDataPartida(r.getDataPartida());
            dto.setNotes(r.getNotes());

            userRepository.findById(r.getGuanyadorId())
                    .ifPresent(u -> dto.setNomGuanyador(u.getNom()));
            userRepository.findById(r.getPerdedorId())
                    .ifPresent(u -> dto.setNomPerdedor(u.getNom()));

            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error en convertir el resultat a resposta: " + e.getMessage(), e);
        }
    }
}