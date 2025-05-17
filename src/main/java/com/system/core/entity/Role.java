package com.system.core.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.system.core.config.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    USER(Set.of(Permission.COWORKSPACE_READ)),
    // Dans la classe Role
    PARTNER(Set.of(
            Permission.COWORKSPACE_READ,
            Permission.COWORKSPACE_CREATE,
            Permission.COWORKSPACE_UPDATE,
            Permission.COWORKSPACE_DELETE,
            Permission.ESPACE_CREATE // Doit correspondre exactement à "espace:create"
    ));

    @Getter
    private final Set<Permission> permissions;

    @JsonValue
    public String getName() {
        return name();
    }

    @JsonCreator
    public static Role fromName(String name) {
        return valueOf(name.toUpperCase());
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        // Ajout du rôle avec le préfixe ROLE_
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}