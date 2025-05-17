package com.system.core.mapper;

import com.system.core.dto.EspaceDTO;
import com.system.core.entity.*;
import java.util.Base64;

public class EspaceMapper {
    public static EspaceDTO toDTO(Espace entity) {
        EspaceDTO dto = new EspaceDTO();
        dto.setIdEspace(entity.getIdEspace());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        dto.setNbretype(entity.getNbretype());
        dto.setCapacite(entity.getCapacite());
        dto.setPrixParMois(entity.getPrixParMois());

        if (entity.getImageData() != null) {
            dto.setImageData(Base64.getEncoder().encodeToString(entity.getImageData()));
        }

        if (entity.getCoWorkspace() != null) {
            dto.setCoWorkspaceId(entity.getCoWorkspace().getIdCoWorkspace());
        }

        return dto;
    }
}