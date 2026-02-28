package com.padelhub.padel_app.controller;

import com.padelhub.padel_app.dto.response.EstadistiquesResponse;
import com.padelhub.padel_app.service.EstadistiquesService;
import com.padelhub.padel_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadistiques")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Estadístiques", description = "Consulta d'estadístiques de rendiment dels jugadors")
public class EstadistiquesController {

    @Autowired
    private EstadistiquesService estadistiquesService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Obtenir les meves estadístiques")
    @GetMapping("/me")
    public ResponseEntity<EstadistiquesResponse> getMevesEstadistiques(
            @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userService.findByEmail(userDetails.getUsername()).getId();
        return ResponseEntity.ok(estadistiquesService.getEstadistiquesUsuari(userId));
    }

    @Operation(summary = "Obtenir les estadístiques d'un usuari per ID")
    @GetMapping("/{userId}")
    public ResponseEntity<EstadistiquesResponse> getEstadistiquesUsuari(@PathVariable String userId) {
        return ResponseEntity.ok(estadistiquesService.getEstadistiquesUsuari(userId));
    }
}
