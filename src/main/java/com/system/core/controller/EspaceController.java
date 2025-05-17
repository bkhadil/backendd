package  com.system.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.core.entity.CoWorkspace;
import com.system.core.entity.Espace;
import com.system.core.service.EspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/coworkspaces")
public class EspaceController {

    @Autowired
    private EspaceService espaceService;

    @PostMapping(value = "/espaces/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createEspace(
            @RequestParam("type") String type,
            @RequestParam("description") String description,
            @RequestParam("nbretype") int nbretype,
            @RequestParam("capacite") int capacite,
            @RequestParam("prix_par_mois") double prixParMois,
            @RequestParam("coWorkspace") String coWorkspaceJson,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        try {
            // Log pour vérifier si l'image est reçue
            if (image != null) {
                System.out.println("Image reçue : " + image.getOriginalFilename());
                System.out.println("Taille de l'image : " + image.getSize() + " bytes");
            } else {
                System.out.println("Aucune image reçue.");
            }

            // Convertir la chaîne JSON en objet CoWorkspace
            CoWorkspace coWorkspace = new ObjectMapper().readValue(coWorkspaceJson, CoWorkspace.class);

            // Créer un nouvel espace
            Espace espace = new Espace();
            espace.setType(type);
            espace.setDescription(description);
            espace.setNbretype(nbretype);
            espace.setCapacite(capacite);
            espace.setPrixParMois(prixParMois);
            espace.setCoWorkspace(coWorkspace);

            // Gérer l'image
            if (image != null && !image.isEmpty()) {
                espace.setImageData(image.getBytes()); // Convertir l'image en tableau de bytes
            }

            Espace savedEspace = espaceService.createEspace(espace);
            return ResponseEntity.ok(savedEspace);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de l'espace.");
        }
    }




    @PutMapping(value = "/espaces/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateEspace(
            @PathVariable("id") Long id,
            @RequestPart("espace") String espaceJson,
            @RequestPart(value = "image", required = false) MultipartFile file) {

        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body("ID d'espace invalide");
            }

            // Log the incoming JSON
            System.out.println("Received JSON: " + espaceJson);

            // Deserialize JSON to Espace
            Espace updatedEspace = new ObjectMapper().readValue(espaceJson, Espace.class);

            // Log the updated Espace object
            System.out.println("Updated Espace: " + updatedEspace);

            // Update the espace
            Espace result = espaceService.update(id, updatedEspace, file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur de traitement: " + e.getMessage());
        }
    }





















    @GetMapping("/espaces/{id}")
    public ResponseEntity<Espace> getEspaceById(@PathVariable Long id) {
        Espace espace = espaceService.findById(id);
        if (espace != null) {
            return ResponseEntity.ok(espace);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
