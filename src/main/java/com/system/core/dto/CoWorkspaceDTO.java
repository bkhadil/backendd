package com.system.core.dto;

import com.system.core.dto.EquipmentDTO;
import com.system.core.dto.EspaceDTO;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class CoWorkspaceDTO {
    private Long idCoWorkspace;
    private String nom;
    private String ville;
    private String adresse;
    private Date dateCreation;
    private String description;
    private String imageData; // Base64
    private Integer partnerId; // Type changé
    private List<EquipmentDTO> equipments;
    private List<EspaceDTO> espaces;
}