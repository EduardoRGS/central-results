package com.results.central_results.application.service;

import com.results.central_results.web.dto.ExamDTO;
import com.results.central_results.domain.event.ExamEventProducer;
import com.results.central_results.domain.exception.ExamNotFoundException;
import com.results.central_results.domain.exception.PatientNotFoundException;
import com.results.central_results.domain.model.Exam;
import com.results.central_results.domain.model.ExamStatus;
import com.results.central_results.domain.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.results.central_results.domain.repository.ExamRepository;
import com.results.central_results.domain.repository.PatientRepository;
import com.results.central_results.infrastructure.metrics.CustomMetrics;

import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepo;
    private final PatientRepository patientRepo;
    private final CustomMetrics metrics;
    private final ExamEventProducer eventProducer;

    public ExamService(ExamRepository examRepo, PatientRepository patientRepo, CustomMetrics metrics, ExamEventProducer eventProducer) {
        this.examRepo = examRepo;
        this.patientRepo = patientRepo;
        this.metrics = metrics;
        this.eventProducer = eventProducer;
    }

    public ExamDTO create(ExamDTO dto) {
        Patient patient = patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(dto.getPatientId()));

        Exam exam = new Exam();
        exam.setPatient(patient);
        exam.setType(dto.getType());
        exam.setResult(dto.getResult());
        exam.setStatus(dto.getStatus());
        exam.setCompletedAt(dto.getCompletedAt());

        Exam savedExam = examRepo.save(exam);
        eventProducer.sendExamCreated(savedExam);
        metrics.incrementExamCreated();
        return toDTO(savedExam);
    }

    public List<ExamDTO> findAll() {
        return examRepo.findAll().stream().map(this::toDTO).toList();
    }

    public Page<ExamDTO> findAll(Pageable pageable) {
        return examRepo.findAll(pageable).map(this::toDTO);
    }

    @Cacheable(value = "exams", key = "#id")
    public ExamDTO findById(Long id) {
        return toDTO(examRepo.findById(id).orElseThrow(() -> new ExamNotFoundException(id)));
    }

    public ExamDTO update(Long id, ExamDTO dto) {
        Exam exam = examRepo.findById(id).orElseThrow(() -> new ExamNotFoundException(id));

        Patient patient = patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(dto.getPatientId()));

        exam.setPatient(patient);
        exam.setType(dto.getType());
        exam.setResult(dto.getResult());
        exam.setStatus(dto.getStatus());
        exam.setCompletedAt(dto.getCompletedAt());

        Exam updatedExam = examRepo.save(exam);
        ExamDTO updated = toDTO(updatedExam);
        if (updated.getStatus() == ExamStatus.COMPLETED) {
            eventProducer.sendExamCompleted(updatedExam);
            metrics.incrementExamCompleted();
        }
        return updated;
    }

    @Cacheable(value = "exams", key = "#id")
    public void delete(Long id) {
        examRepo.deleteById(id);
    }

    private ExamDTO toDTO(Exam e) {
        return ExamDTO.builder()
                .id(e.getId())
                .patientId(e.getPatient().getId())
                .type(e.getType())
                .result(e.getResult())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .completedAt(e.getCompletedAt())
                .build();
    }
}
