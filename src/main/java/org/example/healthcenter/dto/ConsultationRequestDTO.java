package org.example.healthcenter.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultationRequestDTO(
        @NotNull(message = "Patient ID is required") Long patientId,
        @NotNull(message = "Doctor ID is required") Long doctorId,
        @NotNull(message = "Consultation date is required") LocalDateTime consultationDate,
        String notes
) {
}
