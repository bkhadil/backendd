package com.system.core.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

@Getter
@RequiredArgsConstructor
public enum Permission {
    COWORKSPACE_READ("coworkspace:read"),
    COWORKSPACE_CREATE("coworkspace:create"),
    COWORKSPACE_UPDATE("coworkspace:update"),
    COWORKSPACE_DELETE("coworkspace:delete"),
    ESPACE_CREATE("espace:create");


    private final String permission;
}