package com.system.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipments")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipment")
    private Long idEquipment;

    @Column(name = "nom", nullable = false)
    private String nom;

    @JsonIgnore
    @ManyToMany(mappedBy = "equipments")
    private List<CoWorkspace> coWorkspaces = new ArrayList<>();
}