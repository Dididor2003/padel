package com.padelhub.padel_app.repository;

import com.padelhub.padel_app.model.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends MongoRepository<Reserva, String> {

    List<Reserva> findByJugador1IdOrJugador2Id(String jugador1Id, String jugador2Id);

    List<Reserva> findByPistaIdAndDataHoraBetween(String pistaId, LocalDateTime inici, LocalDateTime fi);

    List<Reserva> findByEstat(Reserva.EstatReserva estat);
}
