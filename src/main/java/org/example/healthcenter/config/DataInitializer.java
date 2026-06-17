package org.example.healthcenter.config;

import org.example.healthcenter.model.Doctor;
import org.example.healthcenter.model.Patient;
import org.example.healthcenter.repository.DoctorRepository;
import org.example.healthcenter.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public DataInitializer(DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public void run(String... args) {
        if (doctorRepository.count() == 0) {
            doctorRepository.saveAll(List.of(
                    Doctor.builder()
                            .name("Dr. Carlos Santos")
                            .crm("CRM-SP-12345")
                            .specialty("Cardiologist")
                            .build(),
                    Doctor.builder()
                            .name("Dr. Ana Lima")
                            .crm("CRM-SP-67890")
                            .specialty("Orthopedist")
                            .build()
            ));
        }

        if (patientRepository.count() == 0) {
            patientRepository.saveAll(List.of(
                    Patient.builder()
                            .name("João Silva")
                            .cpf("12345678901")
                            .birthDate(LocalDate.of(1985, 3, 15))
                            .phone("11999990001")
                            .build(),
                    Patient.builder()
                            .name("Maria Oliveira")
                            .cpf("98765432100")
                            .birthDate(LocalDate.of(1992, 7, 22))
                            .phone("11999990002")
                            .build()
            ));
        }
    }
}
