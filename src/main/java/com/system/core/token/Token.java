package com.system.core.token;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import com.system.core.entity.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    // Utilisez le type primitif boolean
    private boolean expired;
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    // Lombok générera automatiquement ces méthodes avec @Data
    // Mais vous pouvez les ajouter manuellement si nécessaire :
    public boolean isExpired() {
        return expired;
    }

    public boolean isRevoked() {
        return revoked;
    }
}