package com.padelhub.padel_app.service.impl;

import com.padelhub.padel_app.dto.request.ActualitzarUserRequest;
import com.padelhub.padel_app.dto.request.RegisterRequest;
import com.padelhub.padel_app.dto.response.UserResponse;
import com.padelhub.padel_app.exception.BadRequestException;
import com.padelhub.padel_app.exception.RecursoNoEncontradoException;
import com.padelhub.padel_app.model.User;
import com.padelhub.padel_app.repository.UserRepository;
import com.padelhub.padel_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registrar(RegisterRequest request) {
        try {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Ja existeix un usuari amb aquest email");
            }

            User user = new User();
            user.setNom(request.getNom());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEdat(request.getEdat());
            user.setSexe(request.getSexe());
            user.setNivell(request.getNivell() != null ? request.getNivell() : User.NivellJoc.BASIC);
            user.setRol(User.Role.USER);
            user.setDataRegistre(LocalDateTime.now());
            user.setEsperantParella(false);
            user.setTotalPartides(0);
            user.setPartidesGuanyades(0);

            return toResponse(userRepository.save(user));

        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperat en registrar l'usuari: " + e.getMessage(), e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuari no trobat: " + email));
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error en cercar l'usuari per email: " + e.getMessage(), e);
        }
    }

    @Override
    public UserResponse getPerfil(String id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuari no trobat"));
            return toResponse(user);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir el perfil de l'usuari: " + e.getMessage(), e);
        }
    }

    @Override
    public UserResponse actualitzarPerfil(String id, ActualitzarUserRequest request) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuari no trobat"));
            user.setNom(request.getNom());
            user.setEdat(request.getEdat());
            user.setSexe(request.getSexe());
            user.setNivell(request.getNivell());
            return toResponse(userRepository.save(user));
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error en actualitzar el perfil de l'usuari: " + e.getMessage(), e);
        }
    }

    @Override
    public List<UserResponse> llistarTots() {
        try {
            return userRepository.findAll().stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error en obtenir la llista d'usuaris: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(String id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new RecursoNoEncontradoException("Usuari no trobat");
            }
            userRepository.deleteById(id);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error en eliminar l'usuari: " + e.getMessage(), e);
        }
    }

    @Override
    public UserResponse canviarRol(String id, String nouRol) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuari no trobat"));
            user.setRol(User.Role.valueOf(nouRol.toUpperCase()));
            return toResponse(userRepository.save(user));
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("El rol '" + nouRol + "' no és vàlid");
        } catch (Exception e) {
            throw new RuntimeException("Error en canviar el rol de l'usuari: " + e.getMessage(), e);
        }
    }

    @Override
    public UserResponse toResponse(User user) {
        try {
            UserResponse dto = new UserResponse();
            dto.setId(user.getId());
            dto.setNom(user.getNom());
            dto.setEmail(user.getEmail());
            dto.setEdat(user.getEdat());
            dto.setSexe(user.getSexe());
            dto.setNivell(user.getNivell());
            dto.setRol(user.getRol() != null ? user.getRol().name() : "USER");
            dto.setTotalPartides(user.getTotalPartides());
            dto.setPartidesGuanyades(user.getPartidesGuanyades());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error en convertir l'usuari a resposta: " + e.getMessage(), e);
        }
    }
}