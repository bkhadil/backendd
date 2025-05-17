package com.system.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.core.dto.CoWorkspaceDTO;
import com.system.core.dto.EquipmentDTO;
import com.system.core.entity.*;
import org.springframework.http.ResponseEntity;         // Pour le type de retour ResponseEntity
import java.util.Map;                                   // Pour utiliser Map.of()
import com.system.core.repository.CoWorkspaceRepository;
import com.system.core.repository.EquipmentRepository;
import com.system.core.service.CoWorkspaceService;
import com.system.core.service.EspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Co-Workspaces", description = "Gestion des espaces de coworking")
@RestController
@RequestMapping("/api/coworkspaces")
public class CoWorkspaceController {

    @Autowired
    private EspaceService espaceService;

    @Autowired
    private CoWorkspaceService coWorkspaceService;

    @Autowired
    private CoWorkspaceRepository coWorkspaceRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;
    @Operation(
            summary = "Créer un nouvel espace de coworking",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "image", description = "Image de l'espace", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)),
                    @Parameter(name = "equipmentNames", description = "Liste des équipements au format JSON")
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Espace créé avec succès",
                    content = @Content(schema = @Schema(implementation = CoWorkspace.class))),
            @ApiResponse(responseCode = "403", description = "Accès non autorisé")
    })
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCoworkspace(
            @RequestParam("nom") String nom,
            @RequestParam("ville") String ville,
            @RequestParam("adresse") String adresse,
            @RequestParam("dateCreation") String dateCreation,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image,
            @RequestParam("equipmentNames") String equipmentNamesJson) {

        try {
            // Convert JSON to EquipmentDTO list
            ObjectMapper objectMapper = new ObjectMapper();
            List<EquipmentDTO> equipmentDTOs = objectMapper.readValue(
                    equipmentNamesJson,
                    new TypeReference<List<EquipmentDTO>>() {}
            );

            List<Equipment> equipments = equipmentDTOs.stream()
                    .map(dto -> {
                        Equipment equipment = new Equipment();
                        equipment.setNom(dto.getNom());
                        return equipment;
                    })
                    .collect(Collectors.toList());

            // Create CoWorkspace entity
            CoWorkspace coWorkspace = new CoWorkspace();
            coWorkspace.setNom(nom);
            coWorkspace.setVille(ville);
            coWorkspace.setAdresse(adresse);
            coWorkspace.setDateCreation(new SimpleDateFormat("yyyy-MM-dd").parse(dateCreation));
            coWorkspace.setDescription(description);
            coWorkspace.setImageData(image.getBytes());

            // Save and return DTO
            CoWorkspaceDTO savedDTO = coWorkspaceService.createCoworkspace(coWorkspace, equipments);
            return ResponseEntity.ok(savedDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<List<CoWorkspaceDTO>> getMyCoworkspaces() {
        List<CoWorkspaceDTO> dtos = coWorkspaceService.getCoworkspacesForCurrentPartner();
        return ResponseEntity.ok(dtos);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoworkspace(@PathVariable Long id) {
        try {
            coWorkspaceService.deleteCoworkspace(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<CoWorkspaceDTO> getDetails(@PathVariable Long id) {
        CoWorkspaceDTO dto = coWorkspaceService.findById(id);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/search")
    //@PreAuthorize("hasAuthority('coworkspace:read')")
    public ResponseEntity<List<CoWorkspace>> searchCoworkspaces(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String searchQuery) {
        return ResponseEntity.ok(coWorkspaceService.searchCoworkspaces(city, searchQuery));
    }



    @GetMapping("/equipments")
    //@PreAuthorize("hasAuthority('coworkspace:read')")
    public ResponseEntity<List<Equipment>> getAllEquipments() {
        return ResponseEntity.ok(equipmentRepository.findAll());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('coworkspace:update')")
    public ResponseEntity<CoWorkspace> updateCoworkspace(
            @PathVariable Long id,
            @RequestPart("coWorkspace") String coWorkspaceJson,
            @RequestPart(value = "image", required = false) MultipartFile file) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Configure date format
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            CoWorkspace updatedCoworkspace = objectMapper.readValue(coWorkspaceJson, CoWorkspace.class);

            return ResponseEntity.ok(coWorkspaceService.updateCoworkspace(id, updatedCoworkspace, file));
        } catch (Exception e) {
            e.printStackTrace(); // Log the exact error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Gestion des Espaces
    @DeleteMapping("/espaces/{id}")
    @PreAuthorize("hasAuthority('coworkspace:delete')")
    public ResponseEntity<Void> deleteEspace(@PathVariable Long id) {
        espaceService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
