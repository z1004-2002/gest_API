package com.vetrix.gest_API.projet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet,Integer> {
    List<Projet> findByEtape(Etape etape);

    @Query("select count(p) from Projet p where p.etape = ?1")
    Integer count_project(Etape etape);
}
