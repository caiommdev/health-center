package org.example.healthcenter.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PatientRequestDTO(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "CPF is required") String cpf,
        LocalDate birthDate,
        String phone
) {
}
