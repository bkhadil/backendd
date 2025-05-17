package com.system.core.dto;

import lombok.Data;

@Data
public class EspaceDTO {
    private Long idEspace;
    private String type;
    private String description;
    private int nbretype;
    private int capacite;
    private double prixParMois;
    private String imageData; // Base64
    private Long coWorkspaceId;
}