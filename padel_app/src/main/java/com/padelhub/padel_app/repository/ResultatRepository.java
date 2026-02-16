package com.padelhub.padel_app.repository;

import com.padelhub.padel_app.model.Resultat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultatRepository extends MongoRepository<Resultat, String> {

    List<Resultat> findByGuanyadorIdOrPerdedorId(String guanyadorId, String perdedorId);

    Optional<Resultat> findByReservaId(String reservaId);
}
