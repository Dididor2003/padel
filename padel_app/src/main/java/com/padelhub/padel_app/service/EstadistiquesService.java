package com.padelhub.padel_app.service;

import com.padelhub.padel_app.dto.response.EstadistiquesResponse;

public interface EstadistiquesService {

    EstadistiquesResponse getEstadistiquesUsuari(String userId);
}
