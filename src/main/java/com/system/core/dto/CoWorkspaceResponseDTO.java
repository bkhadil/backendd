package com.system.core.dto; // Préférable de créer un package dédié

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate; // Préférable à java.util.Date
import java.util.List;
@Getter
@Setter
@Schema(description = "Réponse d'un espace de coworking")
public class CoWorkspaceResponseDTO {
    @Schema(description = "ID unique de l'espace", example = "123")
    private Long id;

    @Schema(description = "Nom de l'espace", example = "Espace Créatif Paris")
    private String nom;

    @Schema(description = "Ville d'implantation", example = "Paris")
    private String ville;

    @Schema(description = "Adresse complète", example = "12 Rue de la Innovation, 75001")
    private String adresse;

    @Schema(description = "Date de création", example = "2024-01-01")
    private LocalDate dateCreation; // Utilisation de LocalDate

    @Schema(description = "Description détaillée", example = "Espace moderne avec équipements high-tech")
    private String description;

    @Schema(description = "URL de l'image principale", example = "https://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "Liste des équipements disponibles")
    private List<String> equipments;

    @Schema(description = "Liste des espaces associés")
    private List<EspaceResponseDTO> espaces; // Nécessite l'import correct
}