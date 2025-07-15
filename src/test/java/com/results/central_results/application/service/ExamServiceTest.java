package com.results.central_results.application.service;

import com.results.central_results.domain.exception.ExamNotFoundException;
import com.results.central_results.domain.exception.PatientNotFoundException;
import com.results.central_results.domain.event.ExamEventProducer;
import com.results.central_results.domain.model.Exam;
import com.results.central_results.domain.model.ExamStatus;
import com.results.central_results.domain.model.Patient;
import com.results.central_results.domain.repository.ExamRepository;
import com.results.central_results.domain.repository.PatientRepository;
import com.results.central_results.web.dto.ExamDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ExamEventProducer eventProducer;

    @InjectMocks
    private ExamService examService;

    private Patient patient;
    private Exam exam;
    private ExamDTO examDTO;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setName("João Silva");
        patient.setEmail("joao@email.com");

        exam = new Exam();
        exam.setId(1L);
        exam.setPatient(patient);
        exam.setType("Hemograma");
        exam.setStatus(ExamStatus.PENDING);
        exam.setCreatedAt(LocalDateTime.now());

        examDTO = ExamDTO.builder()
                .id(1L)
                .patientId(1L)
                .type("Hemograma")
                .status(ExamStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldCreateExamSuccessfully() {
        // Given
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        // When
        ExamDTO result = examService.create(examDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo("Hemograma");
        verify(eventProducer).sendExamCreated(any(Exam.class));
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFound() {
        // Given
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> examService.create(examDTO))
                .isInstanceOf(PatientNotFoundException.class)
                .hasMessageContaining("Paciente com ID 1 não encontrado");
    }

    @Test
    void shouldFindExamByIdSuccessfully() {
        // Given
        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        // When
        ExamDTO result = examService.findById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getType()).isEqualTo("Hemograma");
    }

    @Test
    void shouldThrowExceptionWhenExamNotFound() {
        // Given
        when(examRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> examService.findById(1L))
                .isInstanceOf(ExamNotFoundException.class)
                .hasMessageContaining("Exame com ID 1 não encontrado");
    }

    @Test
    void shouldFindAllExams() {
        // Given
        List<Exam> exams = List.of(exam);
        when(examRepository.findAll()).thenReturn(exams);

        // When
        List<ExamDTO> result = examService.findAll();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo("Hemograma");
    }

    @Test
    void shouldUpdateExamSuccessfully() {
        // Given
        ExamDTO updateDTO = ExamDTO.builder()
                .patientId(1L)
                .type("Hemograma Atualizado")
                .status(ExamStatus.COMPLETED)
                .build();

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        // When
        ExamDTO result = examService.update(1L, updateDTO);

        // Then
        assertThat(result).isNotNull();
        verify(eventProducer).sendExamCreated(any(Exam.class));
    }

    @Test
    void shouldDeleteExamSuccessfully() {
        // Given
        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        // When
        examService.delete(1L);

        // Then
        verify(examRepository).deleteById(1L);
    }
} 