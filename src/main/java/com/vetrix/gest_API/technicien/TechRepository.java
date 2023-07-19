package com.vetrix.gest_API.technicien;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechRepository extends JpaRepository<Technicien,Integer> {
}
