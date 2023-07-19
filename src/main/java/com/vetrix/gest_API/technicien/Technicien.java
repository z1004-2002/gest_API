package com.vetrix.gest_API.technicien;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vetrix.gest_API.projet.Projet;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "technicien")
public class Technicien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String email;
    private String numero;
    private String entreprise;
    @ManyToOne
    @JoinColumn(name = "projet_id",nullable = false)
    @JsonIgnore
    private Projet projet;
}
