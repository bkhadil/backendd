package com.system.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "Réponse d'un espace individuel")
public class EspaceResponseDTO {
    @Schema(description = "ID de l'espace", example = "456")
    private Long id;

    @Schema(description = "Type d'espace", example = "Bureau privé")
    private String type;

    @Schema(description = "Capacité maximale", example = "4")
    private int capacite;

    @Schema(description = "Prix mensuel", example = "450.00")
    private BigDecimal prixParMois;
}