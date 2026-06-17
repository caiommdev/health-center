package org.example.healthcenter.dto;

public record DoctorResponseDTO(
        Long id,
        String name,
        String crm,
        String specialty
) {
}
