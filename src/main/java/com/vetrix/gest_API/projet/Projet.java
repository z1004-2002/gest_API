package com.vetrix.gest_API.projet;

import com.vetrix.gest_API.technicien.Technicien;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numero;
    private Date date_creation;
    private String ville;
    private String quartier;
    private String description;
    private String gps;
    private String type;
    @OneToMany(targetEntity = Technicien.class,mappedBy = "projet")
    private List<Technicien> tech;
    private String bon_achat;
    private String bon_commande;
    private String num_service;
    private String num_accord;
    private Date date_travaux;
    private Date date_fin_travaux;
    private Etape etape;
}
