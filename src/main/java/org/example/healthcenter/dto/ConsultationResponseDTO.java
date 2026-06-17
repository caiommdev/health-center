package org.example.healthcenter.dto;

import java.time.LocalDateTime;

public record ConsultationResponseDTO(
        Long id,
        LocalDateTime consultationDate,
        String notes,
        Long patientId,
        Long doctorId
) {
}
