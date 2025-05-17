package com.system.core.controller;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstname;
    private String lastname;
    private String currentPassword;
    private String newPassword;
    // Retirer le champ email
}