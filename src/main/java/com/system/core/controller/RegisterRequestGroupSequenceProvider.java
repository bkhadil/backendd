package com.system.core.controller;

import com.system.core.entity.Role;
import jakarta.validation.groups.Default;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import java.util.ArrayList;
import java.util.List;

public class RegisterRequestGroupSequenceProvider implements DefaultGroupSequenceProvider<RegisterRequest> {

    @Override
    public List<Class<?>> getValidationGroups(RegisterRequest request) {
        List<Class<?>> groups = new ArrayList<>();

        // Groupe de base pour les contraintes communes
        groups.add(RegisterRequest.class);

        if (request != null) {
            if (request.getRole() == Role.PARTNER) {
                groups.add(PartnerValidation.class); // Validation partenaire
            } else {
                groups.add(UserValidation.class); // Validation utilisateur
            }
        }

        return groups;
    }
}