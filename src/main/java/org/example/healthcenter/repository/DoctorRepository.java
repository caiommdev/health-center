package org.example.healthcenter.repository;

import org.example.healthcenter.dto.DoctorRankingDTO;
import org.example.healthcenter.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
            SELECT new org.example.healthcenter.dto.DoctorRankingDTO(
                d.id, d.name, d.crm, d.specialty, COUNT(c.id)
            )
            FROM Doctor d
            LEFT JOIN d.consultations c
            GROUP BY d.id, d.name, d.crm, d.specialty
            ORDER BY COUNT(c.id) DESC
            """)
    List<DoctorRankingDTO> findDoctorsRanking();
}
