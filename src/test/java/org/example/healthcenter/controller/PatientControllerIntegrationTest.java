package org.example.healthcenter.controller;

import tools.jackson.databind.ObjectMapper;
import org.example.healthcenter.dto.PatientRequestDTO;
import org.example.healthcenter.model.Patient;
import org.example.healthcenter.repository.ConsultationRepository;
import org.example.healthcenter.repository.HospitalizationRepository;
import org.example.healthcenter.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = "ADMIN")
class PatientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private HospitalizationRepository hospitalizationRepository;

    @BeforeEach
    void setUp() {
        consultationRepository.deleteAll();
        hospitalizationRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @Test
    void testCreatePatient() throws Exception {
        PatientRequestDTO dto = new PatientRequestDTO(
                "John Doe", "12345678901", LocalDate.of(1990, 1, 1), "11999999999");

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.cpf").value("12345678901"));
    }

    @Test
    void testFindPatientById() throws Exception {
        Patient patient = patientRepository.save(
                Patient.builder()
                        .name("Jane Doe")
                        .cpf("98765432100")
                        .birthDate(LocalDate.of(1992, 7, 22))
                        .phone("11988888888")
                        .build()
        );

        mockMvc.perform(get("/patients/{id}", patient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patient.getId()))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.cpf").value("98765432100"));
    }

    @Test
    void testFindAllPatients() throws Exception {
        patientRepository.saveAll(List.of(
                Patient.builder().name("John Doe").cpf("11111111111")
                        .birthDate(LocalDate.of(1990, 1, 1)).build(),
                Patient.builder().name("Jane Doe").cpf("22222222222")
                        .birthDate(LocalDate.of(1992, 7, 22)).build()
        ));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    void testDeletePatient() throws Exception {
        Patient patient = patientRepository.save(
                Patient.builder()
                        .name("Bob Smith")
                        .cpf("33333333333")
                        .birthDate(LocalDate.of(1985, 3, 10))
                        .build()
        );

        mockMvc.perform(delete("/patients/{id}", patient.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/patients/{id}", patient.getId()))
                .andExpect(status().isNotFound());
    }
}
