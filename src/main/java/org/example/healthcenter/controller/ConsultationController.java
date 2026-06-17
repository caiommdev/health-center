package org.example.healthcenter.controller;

import jakarta.validation.Valid;
import org.example.healthcenter.dto.ConsultationRequestDTO;
import org.example.healthcenter.dto.ConsultationResponseDTO;
import org.example.healthcenter.service.ConsultationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultations")
public class ConsultationController {

    private final ConsultationService service;

    public ConsultationController(ConsultationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultationResponseDTO create(@Valid @RequestBody ConsultationRequestDTO dto) {
        return service.create(dto);
    }
}
