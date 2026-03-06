package com.padelhub.padel_app.controller;

import com.padelhub.padel_app.dto.request.ActualitzarUserRequest;
import com.padelhub.padel_app.dto.response.UserResponse;
import com.padelhub.padel_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// gestiona els endpoints del perfil d'usuari: consulta i edició pròpia, i operacions d'admin
@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Usuaris", description = "Gestió del perfil d'usuaris")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Obtenir el meu perfil")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMeuPerfil(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.toResponse(userService.findByEmail(userDetails.getUsername())));
    }

    @Operation(summary = "Actualitzar el meu perfil")
    @PutMapping("/me")
    public ResponseEntity<UserResponse> actualitzarMeuPerfil(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ActualitzarUserRequest request) {

    	
        String userId = userService.findByEmail(userDetails.getUsername()).getId();
        return ResponseEntity.ok(userService.actualitzarPerfil(userId, request));
    }

    @Operation(summary = "Obtenir el perfil d'un usuari per ID")
    @GetMapping("/{id}")
    
    public ResponseEntity<UserResponse> getPerfil(@PathVariable String id) {
        return ResponseEntity.ok(userService.getPerfil(id));
    }

    @Operation(summary = "[ADMIN] Llistar tots els usuaris")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> llistarTots() {
        return ResponseEntity.ok(userService.llistarTots());
    }

    @Operation(summary = "[ADMIN] Eliminar un usuari")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    // retorna 204 No Content perquè no hi ha res a retornar després d'esborrar
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        userService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "[ADMIN] Canviar el rol d'un usuari")
    @PutMapping("/{id}/rol")
    @PreAuthorize("hasRole('ADMIN')")
    
    public ResponseEntity<UserResponse> canviarRol(@PathVariable String id, @RequestParam String nouRol) {
        return ResponseEntity.ok(userService.canviarRol(id, nouRol));
    }
}