package com.padelhub.padel_app.controller;

import com.padelhub.padel_app.dto.request.LoginRequest;
import com.padelhub.padel_app.dto.request.RegisterRequest;
import com.padelhub.padel_app.dto.response.JwtResponse;
import com.padelhub.padel_app.dto.response.UserResponse;
import com.padelhub.padel_app.model.User;
import com.padelhub.padel_app.security.JwtUtils;
import com.padelhub.padel_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticació", description = "Endpoints públics de login i registre")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Operation(summary = "Login", description = "Retorna un JWT si les credencials són correctes")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtUtils.generateToken(request.getEmail());
        User user = userService.findByEmail(request.getEmail());

        return ResponseEntity.ok(new JwtResponse(
                token, user.getEmail(), user.getNom(), user.getRol().name()
        ));
    }

    @Operation(summary = "Registre de nou usuari")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registrar(request));
    }
}
