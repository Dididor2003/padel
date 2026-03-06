package com.padelhub.padel_app.controller;

import com.padelhub.padel_app.dto.request.CrearResultatRequest;
import com.padelhub.padel_app.dto.response.ResultatResponse;
import com.padelhub.padel_app.service.ResultatService;
import com.padelhub.padel_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// gestiona els endpoints de resultats: registre de partides jugades i consulta de l'historial
@RestController
@RequestMapping("/api/resultats")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Resultats", description = "Gestió de resultats de partides")
public class ResultatController {

    @Autowired
    private ResultatService resultatService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Registrar el resultat d'una partida")
    @PostMapping
    public ResponseEntity<ResultatResponse> registrar(@Valid @RequestBody CrearResultatRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resultatService.registrarResultat(request));
    }

    @Operation(summary = "Obtenir els meus resultats")
    @GetMapping("/meus")
    public ResponseEntity<List<ResultatResponse>> getMeusResultats(
            @AuthenticationPrincipal UserDetails userDetails) {

        String userId = userService.findByEmail(userDetails.getUsername()).getId();
        return ResponseEntity.ok(resultatService.getHistorialUsuari(userId));
    }

    @Operation(summary = "Obtenir un resultat per ID")
    @GetMapping("/{id}")
    public ResponseEntity<ResultatResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(resultatService.getById(id));
    }

    @Operation(summary = "[ADMIN] Llistar tots els resultats")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResultatResponse>> getTots() {
        return ResponseEntity.ok(resultatService.getTots());
    }
}