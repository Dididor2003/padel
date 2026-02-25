package com.padelhub.padel_app.service;

import com.padelhub.padel_app.dto.request.CrearResultatRequest;
import com.padelhub.padel_app.dto.response.ResultatResponse;

import java.util.List;

public interface ResultatService {

    ResultatResponse registrarResultat(CrearResultatRequest request);

    List<ResultatResponse> getHistorialUsuari(String userId);

    List<ResultatResponse> getTots();

    ResultatResponse getById(String id);
}
