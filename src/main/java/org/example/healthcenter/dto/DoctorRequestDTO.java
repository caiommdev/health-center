package org.example.healthcenter.dto;

import jakarta.validation.constraints.NotBlank;

public record DoctorRequestDTO(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "CRM is required") String crm,
        @NotBlank(message = "Specialty is required") String specialty
) {
}
