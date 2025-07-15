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
public class PatientCreatedEvent {
    private Long patientId;
    private String patientName;
    private String patientEmail;
    private String patientCpf;
    private LocalDateTime createdAt;
    private String eventType = "PATIENT_CREATED";
} 