package com.padelhub.padel_app.service.impl;

import com.padelhub.padel_app.dto.response.EstadistiquesResponse;
import com.padelhub.padel_app.exception.RecursoNoEncontradoException;
import com.padelhub.padel_app.model.User;
import com.padelhub.padel_app.repository.ReservaRepository;
import com.padelhub.padel_app.repository.UserRepository;
import com.padelhub.padel_app.service.EstadistiquesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadistiquesServiceImpl implements EstadistiquesService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public EstadistiquesResponse getEstadistiquesUsuari(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuari no trobat"));

        EstadistiquesResponse stats = new EstadistiquesResponse();
        stats.setUserId(userId);
        stats.setNom(user.getNom());
        stats.setTotalPartides(user.getTotalPartides());
        stats.setPartidesGuanyades(user.getPartidesGuanyades());
        stats.setPartidesPerdes(user.getTotalPartides() - user.getPartidesGuanyades());

        if (user.getTotalPartides() > 0) {
            double pct = (double) user.getPartidesGuanyades() / user.getTotalPartides() * 100;
            stats.setPercentatgeVictories(Math.round(pct * 10.0) / 10.0);
        } else {
            stats.setPercentatgeVictories(0);
        }

        int totalReserves = reservaRepository
                .findByJugador1IdOrJugador2Id(userId, userId).size();
        stats.setTotalReserves(totalReserves);

        return stats;
    }
}
