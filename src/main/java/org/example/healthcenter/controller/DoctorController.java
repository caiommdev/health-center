package org.example.healthcenter.controller;

import jakarta.validation.Valid;
import org.example.healthcenter.dto.DoctorRankingDTO;
import org.example.healthcenter.dto.DoctorRequestDTO;
import org.example.healthcenter.dto.DoctorResponseDTO;
import org.example.healthcenter.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponseDTO create(@Valid @RequestBody DoctorRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<DoctorResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/ranking")
    public List<DoctorRankingDTO> ranking() {
        return service.ranking();
    }
}
