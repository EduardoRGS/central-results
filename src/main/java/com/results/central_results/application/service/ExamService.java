package com.results.central_results.service;

import com.results.central_results.dto.ExamDTO;
import com.results.central_results.domain.event.ExamEventProducer;
import com.results.central_results.domain.model.Exam;
import com.results.central_results.domain.model.ExamStatus;
import com.results.central_results.domain.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.results.central_results.domain.repository.ExamRepository;
import com.results.central_results.domain.repository.PatientRepository;

import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepo;
    private final PatientRepository patientRepo;
    private ExamEventProducer eventProducer;

    public ExamService(ExamRepository examRepo, PatientRepository patientRepo) {
        this.examRepo = examRepo;
        this.patientRepo = patientRepo;
    }

    public ExamDTO create(ExamDTO dto) {
        Patient patient = patientRepo.findById(dto.patientId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Exam exam = new Exam();
        exam.setPatient(patient);
        exam.setType(dto.type());
        exam.setResult(dto.result());
        exam.setStatus(dto.status());
        exam.setCompletedAt(dto.completedAt());

        ExamDTO saved = toDTO(examRepo.save(exam));
        eventProducer.sendExamCreated(saved);
        return saved;
    }

    public List<ExamDTO> findAll() {
        return examRepo.findAll().stream().map(this::toDTO).toList();
    }

    @Cacheable(value = "exams", key = "#id")
    public ExamDTO findById(Long id) {
        return toDTO(examRepo.findById(id).orElseThrow(() -> new RuntimeException("Exame não encontrado")));
    }

    public ExamDTO update(Long id, ExamDTO dto) {
        Exam exam = examRepo.findById(id).orElseThrow(() -> new RuntimeException("Exame não encontrado"));

        Patient patient = patientRepo.findById(dto.patientId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        exam.setPatient(patient);
        exam.setType(dto.type());
        exam.setResult(dto.result());
        exam.setStatus(dto.status());
        exam.setCompletedAt(dto.completedAt());

        ExamDTO updated = toDTO(examRepo.save(exam));
        if (updated.status() == ExamStatus.COMPLETED) {
            eventProducer.sendExamCreated(updated);
        }
        return updated;
    }

    @Cacheable(value = "exams", key = "#id")
    public void delete(Long id) {
        examRepo.deleteById(id);
    }

    private ExamDTO toDTO(Exam e) {
        return new ExamDTO(
                e.getId(),
                e.getPatient().getId(),
                e.getType(),
                e.getResult(),
                e.getStatus(),
                e.getCreatedAt(),
                e.getCompletedAt()
        );
    }


    @Autowired
    public void setEventProducer(ExamEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }
}
