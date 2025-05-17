package com.system.core.controller;

import com.system.core.entity.User;
import com.system.core.repository.CoWorkspaceRepository;
import com.system.core.repository.UserRepository;
import com.system.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/api/users")
    @RequiredArgsConstructor
    public class UserController {
    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    // Gardez cette méthode pour la recherche par email
    @GetMapping("/by-email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }
    // UserController.java
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user) {
        // Charger l'utilisateur complet depuis la base
        User fullUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(fullUser);
    }
    // Endpoint de mise à jour avec ID numérique
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable("userId") Integer id, // Doit être un Integer
            @RequestBody UserUpdateRequest updateRequest,
            @AuthenticationPrincipal User currentUser) {

        // Vérifier que l'ID correspond
        if (!id.equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User updatedUser = userService.updateUser(id, updateRequest);
        return ResponseEntity.ok(updatedUser);
    }
}

