package com.system.core.mapper;

import com.system.core.dto.CoWorkspaceDTO;
import com.system.core.dto.EquipmentDTO;
import com.system.core.entity.*;
import java.util.Base64;
import java.util.stream.Collectors;

public class CoWorkspaceMapper {
    public static CoWorkspaceDTO toDTO(CoWorkspace entity) {
        CoWorkspaceDTO dto = new CoWorkspaceDTO();
        dto.setIdCoWorkspace(entity.getIdCoWorkspace());
        dto.setNom(entity.getNom());
        dto.setVille(entity.getVille());
        dto.setAdresse(entity.getAdresse());
        dto.setDateCreation(entity.getDateCreation());
        dto.setDescription(entity.getDescription());

        if (entity.getImageData() != null) {
            dto.setImageData(Base64.getEncoder().encodeToString(entity.getImageData()));
        }

        if (entity.getPartner() != null) {
            dto.setPartnerId(entity.getPartner().getId()); // Conversion implicite Integer → Integer
        }

        dto.setEquipments(entity.getEquipments().stream()
                .map(equipment -> new EquipmentDTO(equipment.getIdEquipment(), equipment.getNom()))
                .collect(Collectors.toList()));

        dto.setEspaces(entity.getEspaces().stream()
                .map(espace -> EspaceMapper.toDTO(espace))
                .collect(Collectors.toList()));

        return dto;
    }
}