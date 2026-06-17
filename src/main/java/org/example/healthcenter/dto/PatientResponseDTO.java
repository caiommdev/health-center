package org.example.healthcenter.dto;

import java.time.LocalDate;

public record PatientResponseDTO(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate,
        String phone
) {
}
