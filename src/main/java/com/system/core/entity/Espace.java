package com.system.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "espaces")
public class Espace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_espace")
    @JsonProperty("idEspace")
    private Long idEspace;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "nbretype", nullable = false)
    private int nbretype;

    @Column(name = "capacite", nullable = false)
    private int capacite;

    @Column(name = "prix_par_mois", nullable = false)
    @JsonProperty("prix_par_mois")
    private double prixParMois;

    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB") // Utiliser LONGBLOB pour stocker des images
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_co_workspace", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "espaces"}) // Autorise la sérialisation
    private CoWorkspace coWorkspace;

    @JsonIgnore
    @OneToMany(mappedBy = "espace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();
    public double getPrixParMois() {
        return prixParMois;
    }

    public void setPrixParMois(double prixParMois) {
        this.prixParMois = prixParMois;
    }
}