package com.system.core.controller;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Réponse d'erreur")
public class ErrorResponse {

    @Schema(description = "Code d'erreur HTTP", example = "400")
    private int status;

    @Schema(description = "Message d'erreur", example = "Validation failed")
    private String message;

    @Schema(description = "Horodatage de l'erreur", example = "2024-03-20T12:34:56.789Z")
    private LocalDateTime timestamp;
}