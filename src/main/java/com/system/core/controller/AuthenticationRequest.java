package com.system.core.controller;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Requête d'authentification")
public class AuthenticationRequest {

    @Schema(description = "Email de l'utilisateur", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "Mot de passe", example = "password123", required = true)
    private String password;
}
