package org.example.healthcenter.service;

import org.example.healthcenter.dto.DoctorRequestDTO;
import org.example.healthcenter.dto.DoctorResponseDTO;
import org.example.healthcenter.model.Doctor;
import org.example.healthcenter.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository repository;

    @InjectMocks
    private DoctorService service;

    @Test
    void shouldCreateDoctorSuccessfully() {
        DoctorRequestDTO dto = new DoctorRequestDTO("Dr. Carlos Santos", "CRM-SP-12345", "Cardiologist");
        Doctor saved = Doctor.builder()
                .id(1L).name("Dr. Carlos Santos").crm("CRM-SP-12345").specialty("Cardiologist")
                .build();

        when(repository.save(any(Doctor.class))).thenReturn(saved);

        DoctorResponseDTO result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Dr. Carlos Santos", result.name());
        assertEquals("Cardiologist", result.specialty());
        verify(repository, times(1)).save(any(Doctor.class));
    }

    @Test
    void shouldReturnAllDoctors() {
        List<Doctor> doctors = List.of(
                Doctor.builder().id(1L).name("Dr. Carlos Santos").crm("CRM-SP-12345").specialty("Cardiologist").build(),
                Doctor.builder().id(2L).name("Dr. Ana Lima").crm("CRM-SP-67890").specialty("Orthopedist").build()
        );

        when(repository.findAll()).thenReturn(doctors);

        List<DoctorResponseDTO> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("Dr. Carlos Santos", result.get(0).name());
        assertEquals("Orthopedist", result.get(1).specialty());
    }
}
