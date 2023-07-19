package com.vetrix.gest_API.image;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.vetrix.gest_API.projet.Projet;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "image")
public class ImageDao implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String path;
	private Long size;
	private String fileType;
	private String type;
	@ManyToOne
	private Projet projet;
}
