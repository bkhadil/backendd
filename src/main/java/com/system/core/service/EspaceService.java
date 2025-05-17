package com.system.core.service;

import com.system.core.entity.CoWorkspace;
import com.system.core.entity.Espace;
import com.system.core.repository.EspaceRepository;
import com.system.core.repository.CoWorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class EspaceService {

    @Autowired
    private EspaceRepository espaceRepository;

    @Autowired
    private CoWorkspaceRepository coWorkspaceRepository;
    @Transactional
    public Espace createEspace(Espace espace) {
        // Vérifier si le CoWorkspace existe
        if (espace.getCoWorkspace() != null && espace.getCoWorkspace().getIdCoWorkspace() != null) {
            CoWorkspace coWorkspace = coWorkspaceRepository.findById(espace.getCoWorkspace().getIdCoWorkspace())
                    .orElseThrow(() -> new IllegalArgumentException("Coworking space non trouvé."));
            espace.setCoWorkspace(coWorkspace);
        } else {
            throw new IllegalArgumentException("ID du coworking space manquant.");
        }

        return espaceRepository.save(espace);
    }








    public void delete(Long id) {
        espaceRepository.deleteById(id);
    }

    public Espace update(Long id, Espace updatedEspace) {
        Espace existing = espaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espace non trouvé"));
        // Mettre à jour les propriétés
        existing.setType(updatedEspace.getType());
        existing.setNbretype(updatedEspace.getNbretype());
        // ... autres propriétés
        return espaceRepository.save(existing);
    }










    // EspaceService.java
    @Transactional
    public Espace update(Long id, Espace updatedEspace, MultipartFile file) {
        Espace existing = espaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espace non trouvé"));

        // Mettre à jour les champs de base
        existing.setType(updatedEspace.getType());
        existing.setNbretype(updatedEspace.getNbretype());
        existing.setCapacite(updatedEspace.getCapacite());
        existing.setPrixParMois(updatedEspace.getPrixParMois());
        existing.setDescription(updatedEspace.getDescription());

        // Gérer l'image
        if (file != null && !file.isEmpty()) {
            try {
                existing.setImageData(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Erreur de traitement de l'image", e);
            }
        }

        return espaceRepository.save(existing);
    }
    public Espace findById(Long id) {
        return espaceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Espace non trouvé ID: " + id));
    }
}