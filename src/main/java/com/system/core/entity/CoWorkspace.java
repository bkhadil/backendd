package com.system.core.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "coworkspaces")
public class CoWorkspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_co_workspace")
    private Long idCoWorkspace;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "ville", nullable = false)
    private String ville;

    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Column(name = "date_creation", nullable = false)
    @JsonProperty("dateCreation")
    private Date dateCreation;

    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB", nullable = false) // Rendre l'image obligatoire
    private byte[] imageData;
    // Relation One-to-Many avec Espace
    @OneToMany(mappedBy = "coWorkspace", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    private List<Espace> espaces = new ArrayList<>();

    // Relation Many-to-Many avec Equipment

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "coworkspace_equipment",
            joinColumns = @JoinColumn(name = "id_co_workspace"),
            inverseJoinColumns = @JoinColumn(name = "id_equipment")
    )

    private List<Equipment> equipments = new ArrayList<>();



    // Ajouter cette méthode pour définir l'image
    public void setImage(byte[] imageData) {
        this.imageData = imageData;
    }
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private User partner;

    // Ajouter cette méthode pour obtenir l'image
    public byte[] getImage() {
        return this.imageData;
    }
}