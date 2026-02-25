package com.padelhub.padel_app.service;

import com.padelhub.padel_app.dto.request.ActualitzarUserRequest;
import com.padelhub.padel_app.dto.request.RegisterRequest;
import com.padelhub.padel_app.dto.response.UserResponse;
import com.padelhub.padel_app.model.User;

import java.util.List;

public interface UserService {

    UserResponse registrar(RegisterRequest request);

    User findByEmail(String email);

    UserResponse getPerfil(String id);

    UserResponse actualitzarPerfil(String id, ActualitzarUserRequest request);

    List<UserResponse> llistarTots();

    void eliminar(String id);

    UserResponse canviarRol(String id, String nouRol);

    UserResponse toResponse(User user);
}
