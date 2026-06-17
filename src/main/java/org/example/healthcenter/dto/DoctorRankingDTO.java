package org.example.healthcenter.dto;

public record DoctorRankingDTO(
        Long id,
        String name,
        String crm,
        String specialty,
        Long totalConsultations
) {
}
