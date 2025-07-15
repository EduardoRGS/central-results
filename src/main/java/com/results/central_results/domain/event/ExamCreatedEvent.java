package com.results.central_results.domain.event;

import com.results.central_results.domain.model.ExamStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamCreatedEvent {
    private Long examId;
    private Long patientId;
    private String patientName;
    private String examType;
    private ExamStatus status;
    private LocalDateTime createdAt;
    private String eventType = "EXAM_CREATED";
} 