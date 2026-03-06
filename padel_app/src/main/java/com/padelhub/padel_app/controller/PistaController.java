package com.padelhub.padel_app.controller;

import com.padelhub.padel_app.dto.request.CrearPistaRequest;
import com.padelhub.padel_app.dto.response.PistaResponse;
import com.padelhub.padel_app.service.PistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

// gestiona els endpoints de les pistes: consulta pública i operacions d'admin
@RestController
@RequestMapping("/api/pistes")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Pistes", description = "Gestió de pistes de padel")
public class PistaController {

    @Autowired
    private PistaService pistaService;

    @Operation(summary = "Llistar totes les pistes")
    @GetMapping
    public ResponseEntity<List<PistaResponse>> llistarTotes() {
        return ResponseEntity.ok(pistaService.llistarTotes());
    }

    @Operation(summary = "Llistar pistes disponibles")
    @GetMapping("/disponibles")
    public ResponseEntity<List<PistaResponse>> llistarDisponibles() {
        return ResponseEntity.ok(pistaService.llistarDisponibles());
    }

    @Operation(summary = "Consultar pistes disponibles en un horari concret")
    @GetMapping("/disponibles/horari")
    
    public ResponseEntity<List<PistaResponse>> getPistesPerHorari(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHora,
            @RequestParam(defaultValue = "60") int duracioMinuts) {
        return ResponseEntity.ok(pistaService.getPistesDisponiblesPerHorari(dataHora, duracioMinuts));
    }

    @Operation(summary = "Obtenir una pista per ID")
    @GetMapping("/{id}")
    public ResponseEntity<PistaResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(pistaService.getById(id));
    }

    @Operation(summary = "[ADMIN] Crear una nova pista")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PistaResponse> crear(@Valid @RequestBody CrearPistaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pistaService.crear(request));
    }

    @Operation(summary = "[ADMIN] Actualitzar una pista")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PistaResponse> actualitzar(@PathVariable String id, @Valid @RequestBody CrearPistaRequest request) {
        return ResponseEntity.ok(pistaService.actualitzar(id, request));
    }

    @Operation(summary = "[ADMIN] Eliminar una pista")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        pistaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}