package org.example.healthcenter.service;

import org.example.healthcenter.dto.PatientRequestDTO;
import org.example.healthcenter.dto.PatientResponseDTO;
import org.example.healthcenter.model.Patient;
import org.example.healthcenter.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository repository;

    @InjectMocks
    private PatientService service;

    @Test
    void shouldCreatePatientSuccessfully() {
        PatientRequestDTO dto = new PatientRequestDTO("John Doe", "12345678901", LocalDate.of(1990, 1, 1), "11999999999");
        Patient saved = Patient.builder()
                .id(1L).name("John Doe").cpf("12345678901")
                .birthDate(LocalDate.of(1990, 1, 1)).phone("11999999999")
                .build();

        when(repository.save(any(Patient.class))).thenReturn(saved);

        PatientResponseDTO result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("John Doe", result.name());
        assertEquals("12345678901", result.cpf());
        verify(repository, times(1)).save(any(Patient.class));
    }

    @Test
    void shouldFindPatientByIdSuccessfully() {
        Patient patient = Patient.builder()
                .id(1L).name("John Doe").cpf("12345678901")
                .birthDate(LocalDate.of(1990, 1, 1)).phone("11999999999")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(patient));

        PatientResponseDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("John Doe", result.name());
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.findById(99L));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void shouldReturnAllPatients() {
        List<Patient> patients = List.of(
                Patient.builder().id(1L).name("John Doe").cpf("11111111111")
                        .birthDate(LocalDate.of(1990, 1, 1)).build(),
                Patient.builder().id(2L).name("Jane Doe").cpf("22222222222")
                        .birthDate(LocalDate.of(1992, 5, 10)).build()
        );

        when(repository.findAll()).thenReturn(patients);

        List<PatientResponseDTO> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).name());
        assertEquals("Jane Doe", result.get(1).name());
    }

    @Test
    void shouldDeletePatientSuccessfully() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentPatient() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.delete(99L));
        verify(repository, never()).deleteById(any());
    }
}
