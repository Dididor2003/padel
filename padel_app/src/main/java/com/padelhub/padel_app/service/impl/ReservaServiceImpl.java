package com.padelhub.padel_app.service.impl;

import com.padelhub.padel_app.dto.request.CrearReservaRequest;
import com.padelhub.padel_app.dto.response.ReservaResponse;
import com.padelhub.padel_app.exception.BadRequestException;
import com.padelhub.padel_app.exception.RecursoNoEncontradoException;
import com.padelhub.padel_app.model.Pista;
import com.padelhub.padel_app.model.Reserva;
import com.padelhub.padel_app.model.User;
import com.padelhub.padel_app.repository.PistaRepository;
import com.padelhub.padel_app.repository.ReservaRepository;
import com.padelhub.padel_app.repository.UserRepository;
import com.padelhub.padel_app.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PistaRepository pistaRepository;

    @Override
    public ReservaResponse crearReserva(CrearReservaRequest request, String emailJugador1) {
        try {
            User jugador1 = userRepository.findByEmail(emailJugador1)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuari no trobat"));

            Pista pista = pistaRepository.findById(request.getPistaId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Pista no trobada"));

            if (!pista.isDisponible()) {
                throw new BadRequestException("La pista no està disponible");
            }

            comprovarDisponibilitat(request.getPistaId(), request.getDataHora(), request.getDuracioMinuts(), null);

            Reserva reserva = new Reserva();
            reserva.setPistaId(request.getPistaId());
            reserva.setDataHora(request.getDataHora());
            reserva.setDuracioMinuts(request.getDuracioMinuts() > 0 ? request.getDuracioMinuts() : 60);
            reserva.setJugador1Id(jugador1.getId());
            reserva.setDataCreacio(LocalDateTime.now());

            if (request.isAssignacioAutomatica()) {
                Optional<Reserva> parelaEsperant = reservaRepository
                        .findByEstat(Reserva.EstatReserva.ESPERANT_PARELLA)
                        .stream()
                        .filter(r -> !r.getJugador1Id().equals(jugador1.getId()))
                        .filter(r -> r.getDataHora().equals(request.getDataHora()))
                        .filter(r -> r.getPistaId().equals(request.getPistaId()))
                        .findFirst();

                if (parelaEsperant.isPresent()) {
                    Reserva reservaExistent = parelaEsperant.get();
                    reservaExistent.setJugador2Id(jugador1.getId());
                    reservaExistent.setEstat(Reserva.EstatReserva.CONFIRMADA);
                    reservaRepository.save(reservaExistent);

                    userRepository.findById(reservaExistent.getJugador1Id()).ifPresent(u -> {
                        u.setEsperantParella(false);
                        userRepository.save(u);
                    });

                    return toResponse(reservaExistent);
                } else {
                    reserva.setEstat(Reserva.EstatReserva.ESPERANT_PARELLA);
                    jugador1.setEsperantParella(true);
                    userRepository.save(jugador1);
                }

            } else if (request.getJugador2Id() != null && !request.getJugador2Id().isEmpty()) {
                if (!userRepository.existsById(request.getJugador2Id())) {
                    throw new RecursoNoEncontradoException("El segon jugador no existeix");
                }
                reserva.setJugador2Id(request.getJugador2Id());
                reserva.setEstat(Reserva.EstatReserva.CONFIRMADA);
            } else {
                reserva.setEstat(Reserva.EstatReserva.PENDENT);
            }

            return toResponse(reservaRepository.save(reserva));

        } catch (RecursoNoEncontradoException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperat en crear la reserva: " + e.getMessage(), e);
        }
    }

    private void comprovarDisponibilitat(String pistaId, LocalDateTime dataHora,
                                          int duracio, String reservaIdExcloure) {
        try {
            LocalDateTime fi = dataHora.plusMinutes(duracio);
            List<Reserva> conflictes = reservaRepository
                    .findByPistaIdAndDataHoraBetween(pistaId, dataHora.minusMinutes(duracio), fi)
                    .stream()
                    .filter(r -> r.getEstat() != Reserva.EstatReserva.CANCEL_LADA)
                    .filter(r -> reservaIdExcloure == null || !r.getId().equals(reservaIdExcloure))
                    .collect(Collectors.toList());

            if (!conflictes.isEmpty()) {
                throw new BadRequestException("La pista no està disponible en aquell horari");
            }
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error en comprovar la disponibilitat de la pista: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ReservaResponse> getHistorialUsuari(String userId) {
        try {
            return reservaRepository.findByJugador1IdOrJugador2Id(userId, userId)
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir l'historial de l'usuari: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ReservaResponse> getTotes() {
        try {
            return reservaRepository.findAll().stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir totes les reserves: " + e.getMessage(), e);
        }
    }

    @Override
    public ReservaResponse getById(String id) {
        try {
            Reserva r = reservaRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no trobada"));
            return toResponse(r);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir la reserva amb id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public ReservaResponse cancellar(String id, String emailUsuari) {
        try {
            Reserva reserva = reservaRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no trobada"));

            User usuari = userRepository.findByEmail(emailUsuari)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuari no trobat"));

            boolean esAdmin = usuari.getRol() == User.Role.ADMIN;
            boolean esJugador = reserva.getJugador1Id().equals(usuari.getId()) ||
                    (reserva.getJugador2Id() != null && reserva.getJugador2Id().equals(usuari.getId()));

            if (!esAdmin && !esJugador) {
                throw new BadRequestException("No tens permisos per cancel·lar aquesta reserva");
            }

            if (reserva.getEstat() == Reserva.EstatReserva.ESPERANT_PARELLA) {
                userRepository.findById(reserva.getJugador1Id()).ifPresent(u -> {
                    u.setEsperantParella(false);
                    userRepository.save(u);
                });
            }

            reserva.setEstat(Reserva.EstatReserva.CANCEL_LADA);
            return toResponse(reservaRepository.save(reserva));

        } catch (RecursoNoEncontradoException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error en cancel·lar la reserva: " + e.getMessage(), e);
        }
    }

    private ReservaResponse toResponse(Reserva r) {
        try {
            ReservaResponse dto = new ReservaResponse();
            dto.setId(r.getId());
            dto.setPistaId(r.getPistaId());
            dto.setDataHora(r.getDataHora());
            dto.setDuracioMinuts(r.getDuracioMinuts());
            dto.setJugador1Id(r.getJugador1Id());
            dto.setJugador2Id(r.getJugador2Id());
            dto.setEstat(r.getEstat());
            dto.setDataCreacio(r.getDataCreacio());

            pistaRepository.findById(r.getPistaId())
                    .ifPresent(p -> dto.setNomPista(p.getNom()));

            userRepository.findById(r.getJugador1Id())
                    .ifPresent(u -> dto.setNomJugador1(u.getNom()));
            if (r.getJugador2Id() != null) {
                userRepository.findById(r.getJugador2Id())
                        .ifPresent(u -> dto.setNomJugador2(u.getNom()));
            }
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error en convertir la reserva a resposta: " + e.getMessage(), e);
        }
    }
}