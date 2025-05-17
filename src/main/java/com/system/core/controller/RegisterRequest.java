package com.system.core.controller;

import com.system.core.entity.Role;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.group.GroupSequenceProvider;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@GroupSequenceProvider(RegisterRequestGroupSequenceProvider.class)
public class RegisterRequest {
    // Champs communs
    @NotBlank(groups = {UserValidation.class, PartnerValidation.class})
    private String firstname;

    @NotBlank(groups = {UserValidation.class, PartnerValidation.class})
    private String lastname;

    @Email(groups = {UserValidation.class, PartnerValidation.class})
    @NotBlank(groups = {UserValidation.class, PartnerValidation.class})
    private String email;

    @NotBlank(groups = {UserValidation.class, PartnerValidation.class})
    private String password;

    @NotNull(groups = {UserValidation.class, PartnerValidation.class})
    private Role role;

    // Champs spécifiques aux partenaires
    @NotBlank(groups = PartnerValidation.class)
    @Null(groups = UserValidation.class)
    private String companyName;

    @NotBlank(groups = PartnerValidation.class)
    @Pattern(
            regexp = "^\\d{7}[A-Z][ABPFN][MCPN]\\d{3}$",
            message = "Format invalide: NNNNNNNX/X/X/NNN",
            groups = PartnerValidation.class
    )
    @Null(groups = UserValidation.class)
    private String taxIdentificationNumber;
}