package com.padelhub.padel_app.repository;

import com.padelhub.padel_app.model.Pista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PistaRepository extends MongoRepository<Pista, String> {

    List<Pista> findByDisponibleTrue();

    boolean existsByNom(String nom);
}
