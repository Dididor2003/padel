package com.padelhub.padel_app.controller;

import com.padelhub.padel_app.dto.request.CrearReservaRequest;
import com.padelhub.padel_app.dto.response.ReservaResponse;
import com.padelhub.padel_app.service.ReservaService;
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

// gestiona els endpoints de reserves: creació amb o sense assignació automàtica, consulta i cancel·lació
@RestController
@RequestMapping("/api/reserves")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Reserves", description = "Gestió de reserves de pistes de padel")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Crear una nova reserva",
               description = "Si assignacioAutomatica=true, el sistema busca parella automàticament")
    @PostMapping
    public ResponseEntity<ReservaResponse> crearReserva(
            @Valid @RequestBody CrearReservaRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        // passa l'email del token perquè el servei sepa qui és el jugador1
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaService.crearReserva(request, userDetails.getUsername()));
    }

    @Operation(summary = "Obtenir les meves reserves")
    @GetMapping("/meves")
    public ResponseEntity<List<ReservaResponse>> getMevesReserves(@AuthenticationPrincipal UserDetails userDetails) {

        String userId = userService.findByEmail(userDetails.getUsername()).getId();
        return ResponseEntity.ok(reservaService.getHistorialUsuari(userId));
    }

    @Operation(summary = "Obtenir una reserva per ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(reservaService.getById(id));
    }

    @Operation(summary = "Cancel·lar una reserva")
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservaResponse> cancellar(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {

        // el servei comprova internament si l'usuari és admin o és un dels jugadors de la reserva
        return ResponseEntity.ok(reservaService.cancellar(id, userDetails.getUsername()));
    }

    @Operation(summary = "[ADMIN] Llistar totes les reserves")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservaResponse>> getTotes() {
        return ResponseEntity.ok(reservaService.getTotes());
    }
}