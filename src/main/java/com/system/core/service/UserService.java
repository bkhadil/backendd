package com.system.core.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.system.core.controller.UserUpdateRequest;
import com.system.core.entity.User;
import com.system.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User updateUser(Integer id, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Supprimer la partie de modification d'email
        if (updateRequest.getFirstname() != null) {
            user.setFirstname(updateRequest.getFirstname());
        }

        if (updateRequest.getLastname() != null) {
            user.setLastname(updateRequest.getLastname());
        }

        // Supprimer toute la logique de vérification d'email
        // if (updateRequest.getEmail() != null...

        if (updateRequest.getNewPassword() != null && !updateRequest.getNewPassword().isEmpty()) {
            if (!passwordEncoder.matches(updateRequest.getCurrentPassword(), user.getPassword())) {
                throw new RuntimeException("Current password is incorrect");
            }
            user.setPassword(passwordEncoder.encode(updateRequest.getNewPassword()));
        }

        return userRepository.save(user);
    }
}