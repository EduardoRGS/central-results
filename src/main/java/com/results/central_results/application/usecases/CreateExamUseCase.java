package com.results.central_results.application.usecases;

import com.results.central_results.domain.model.Exam;
import com.results.central_results.domain.model.Patient;
import com.results.central_results.domain.repository.ExamRepository;
import com.results.central_results.domain.repository.PatientRepository;
import com.results.central_results.web.dto.ExamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateExamUseCase {

    private final ExamRepository examRepository;
    private final PatientRepository patientRepository;

    public ExamDTO execute(ExamDTO dto) {
        // Validações de negócio
        validateExamData(dto);
        
        // Buscar paciente
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        // Criar exame
        Exam exam = new Exam();
        exam.setPatient(patient);
        exam.setType(dto.getType());
        exam.setResult(dto.getResult());
        exam.setStatus(dto.getStatus());
        exam.setCompletedAt(dto.getCompletedAt());

        // Salvar
        Exam savedExam = examRepository.save(exam);
        
        return toDTO(savedExam);
    }

    private void validateExamData(ExamDTO dto) {
        if (dto.getType() == null || dto.getType().trim().isEmpty()) {
            throw new RuntimeException("Tipo de exame é obrigatório");
        }
        
        if (dto.getPatientId() == null) {
            throw new RuntimeException("ID do paciente é obrigatório");
        }
    }

    private ExamDTO toDTO(Exam exam) {
        return new ExamDTO(
                exam.getId(),
                exam.getPatient().getId(),
                exam.getType(),
                exam.getResult(),
                exam.getStatus(),
                exam.getCreatedAt(),
                exam.getCompletedAt()
        );
    }
} 