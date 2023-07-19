package com.vetrix.gest_API.image;

import java.util.List;

import com.vetrix.gest_API.projet.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<ImageDao, Integer> {
	List<ImageDao> findByProjet(Projet projet);
}
