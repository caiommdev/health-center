package org.example.healthcenter.repository;

import org.example.healthcenter.model.Hospitalization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalizationRepository extends JpaRepository<Hospitalization, Long> {
}
