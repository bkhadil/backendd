    package com.system.core.service;

    import com.system.core.dto.CoWorkspaceDTO;
    import com.system.core.entity.CoWorkspace;
    import com.system.core.entity.Equipment;
    import com.system.core.entity.User;
    import com.system.core.mapper.CoWorkspaceMapper;
    import com.system.core.repository.CoWorkspaceRepository;
    import com.system.core.repository.EquipmentRepository;
    import org.springframework.beans.BeanUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    @Service
    public class CoWorkspaceService {

        @Autowired
        private  CoWorkspaceRepository coWorkspaceRepository;

        @Autowired
        private EquipmentRepository equipmentRepository;



        @Transactional
        public CoWorkspaceDTO createCoworkspace(CoWorkspace coWorkspace, List<Equipment> equipments) {
            // Récupérer l'utilisateur connecté
            User currentPartner = getCurrentUser();

            // Gérer les équipements existants
            List<Equipment> persistedEquipments = new ArrayList<>();
            for (Equipment equipment : equipments) {
                Optional<Equipment> existing = equipmentRepository.findByNom(equipment.getNom());
                persistedEquipments.add(existing.orElseGet(() -> equipmentRepository.save(equipment)));
            }

            // Lier le partenaire et les équipements
            coWorkspace.setPartner(currentPartner);
            coWorkspace.setEquipments(persistedEquipments);

            CoWorkspace saved = coWorkspaceRepository.save(coWorkspace);
            return CoWorkspaceMapper.toDTO(saved);
        }

        private User getCurrentUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (User) authentication.getPrincipal();
        }

        public List<CoWorkspaceDTO> getCoworkspacesForCurrentPartner() {
            User currentUser = getCurrentUser(); // Utilisation de la nouvelle méthode
            return coWorkspaceRepository.findByPartner(currentUser)
                    .stream()
                    .map(CoWorkspaceMapper::toDTO)
                    .collect(Collectors.toList());
        }

        public CoWorkspaceDTO findById(Long id) {
            CoWorkspace entity = coWorkspaceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Coworkspace non trouvé"));
            return CoWorkspaceMapper.toDTO(entity);
        }



















        @Transactional
        public void deleteCoworkspace(Long id) {
            coWorkspaceRepository.deleteById(id);
        }
        @Transactional
        public CoWorkspace updateCoworkspace(Long id, CoWorkspace updatedCoworkspace) {
            CoWorkspace existingCoworkspace = coWorkspaceRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Coworking space non trouvé."));

            // Mettre à jour les propriétés de existingCoworkspace avec celles de updatedCoworkspace
            existingCoworkspace.setNom(updatedCoworkspace.getNom());
            existingCoworkspace.setVille(updatedCoworkspace.getVille());
            existingCoworkspace.setAdresse(updatedCoworkspace.getAdresse());
            existingCoworkspace.setDateCreation(updatedCoworkspace.getDateCreation());
            existingCoworkspace.setDescription(updatedCoworkspace.getDescription());
            existingCoworkspace.setImage(updatedCoworkspace.getImage());

            // Sauvegarder les modifications
            return coWorkspaceRepository.save(existingCoworkspace);
        }


        public List<CoWorkspace> searchCoworkspaces(String city, String searchQuery) {
            String processedCity = (city != null && !city.isEmpty()) ? city : null;
            String processedQuery = (searchQuery != null && !searchQuery.isEmpty()) ? searchQuery : null;
            return coWorkspaceRepository.searchByCityAndQuery(processedCity, processedQuery);
        }








        @Transactional
        public CoWorkspace updateCoworkspace(Long id, CoWorkspace updatedCoworkspace, MultipartFile file) {
            CoWorkspace existing = coWorkspaceRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Coworkspace non trouvé"));

            // Mise à jour manuelle des champs
            existing.setNom(updatedCoworkspace.getNom());
            existing.setVille(updatedCoworkspace.getVille());
            existing.setAdresse(updatedCoworkspace.getAdresse());
            existing.setDateCreation(updatedCoworkspace.getDateCreation());
            existing.setDescription(updatedCoworkspace.getDescription());

            // Gestion de l'image
            if (file != null && !file.isEmpty()) {
                try {
                    existing.setImageData(file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Erreur de lecture du fichier image", e);
                }
            }

            // Mise à jour des équipements
            List<Equipment> persistedEquipments = new ArrayList<>();
            for (Equipment eq : updatedCoworkspace.getEquipments()) {
                Equipment existingEq = equipmentRepository.findByNom(eq.getNom())
                        .orElseGet(() -> equipmentRepository.save(eq));
                persistedEquipments.add(existingEq);
            }
            existing.setEquipments(persistedEquipments);

            return coWorkspaceRepository.save(existing);
        }
    }