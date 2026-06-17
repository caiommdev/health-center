package org.example.healthcenter.service;

import org.example.healthcenter.dto.ConsultationRequestDTO;
import org.example.healthcenter.dto.ConsultationResponseDTO;
import org.example.healthcenter.model.Consultation;
import org.example.healthcenter.model.Doctor;
import org.example.healthcenter.model.Patient;
import org.example.healthcenter.repository.ConsultationRepository;
import org.example.healthcenter.repository.DoctorRepository;
import org.example.healthcenter.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public ConsultationService(ConsultationRepository consultationRepository,
                               PatientRepository patientRepository,
                               DoctorRepository doctorRepository) {
        this.consultationRepository = consultationRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public ConsultationResponseDTO create(ConsultationRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        Doctor doctor = doctorRepository.findById(dto.doctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));

        Consultation consultation = Consultation.builder()
                .consultationDate(dto.consultationDate())
                .notes(dto.notes())
                .patient(patient)
                .doctor(doctor)
                .build();

        Consultation saved = consultationRepository.save(consultation);
        return new ConsultationResponseDTO(
                saved.getId(),
                saved.getConsultationDate(),
                saved.getNotes(),
                saved.getPatient().getId(),
                saved.getDoctor().getId()
        );
    }
}
