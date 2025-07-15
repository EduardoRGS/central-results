package com.results.central_results.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamCompletedEvent {
    private Long examId;
    private Long patientId;
    private String patientName;
    private String patientEmail;
    private String examType;
    private String result;
    private LocalDateTime completedAt;
    private String eventType = "EXAM_COMPLETED";
} 