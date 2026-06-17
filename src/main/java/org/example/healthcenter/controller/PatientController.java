package org.example.healthcenter.controller;

import jakarta.validation.Valid;
import org.example.healthcenter.dto.PatientRequestDTO;
import org.example.healthcenter.dto.PatientResponseDTO;
import org.example.healthcenter.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponseDTO create(@Valid @RequestBody PatientRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public PatientResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<PatientResponseDTO> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
