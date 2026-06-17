package org.example.healthcenter.service;

import org.example.healthcenter.dto.DoctorRankingDTO;
import org.example.healthcenter.dto.DoctorRequestDTO;
import org.example.healthcenter.dto.DoctorResponseDTO;
import org.example.healthcenter.model.Doctor;
import org.example.healthcenter.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public DoctorResponseDTO create(DoctorRequestDTO dto) {
        Doctor doctor = Doctor.builder()
                .name(dto.name())
                .crm(dto.crm())
                .specialty(dto.specialty())
                .build();
        return toResponse(repository.save(doctor));
    }

    public List<DoctorResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<DoctorRankingDTO> ranking() {
        return repository.findDoctorsRanking();
    }

    private DoctorResponseDTO toResponse(Doctor d) {
        return new DoctorResponseDTO(d.getId(), d.getName(), d.getCrm(), d.getSpecialty());
    }
}
