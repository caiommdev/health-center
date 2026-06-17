package org.example.healthcenter.service;

import org.example.healthcenter.dto.PatientRequestDTO;
import org.example.healthcenter.dto.PatientResponseDTO;
import org.example.healthcenter.model.Patient;
import org.example.healthcenter.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public PatientResponseDTO create(PatientRequestDTO dto) {
        Patient patient = Patient.builder()
                .name(dto.name())
                .cpf(dto.cpf())
                .birthDate(dto.birthDate())
                .phone(dto.phone())
                .build();
        return toResponse(repository.save(patient));
    }

    public PatientResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
    }

    public List<PatientResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }
        repository.deleteById(id);
    }

    private PatientResponseDTO toResponse(Patient p) {
        return new PatientResponseDTO(p.getId(), p.getName(), p.getCpf(), p.getBirthDate(), p.getPhone());
    }
}
