package com.system.core.controller;

import com.system.core.entity.Role;
import com.system.core.entity.User;
import com.system.core.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import jakarta.validation.*;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;



import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@Tag(name = "Authentication", description = "API pour l'authentification des utilisateurs")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @Operation(
            summary = "Enregistrer un nouvel utilisateur",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilisateur enregistré avec succès"),
                    @ApiResponse(responseCode = "400", description = "Données d'entrée invalides")
            }
    )


    @PostMapping("/register")
    public ResponseEntity<User> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {
        User savedUser = service.register(request, response);
        return ResponseEntity.ok(savedUser);
    }
    @Operation(
            summary = "Authentifier un utilisateur",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentification réussie"),
                    @ApiResponse(responseCode = "401", description = "Identifiants invalides")
            }
    )

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        AuthenticationResponse authenticationResponse = service.authenticate(request, response);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request
    ) {
        AuthenticationResponse response = service.refreshToken(request);
        return ResponseEntity.ok(response);
    }
}