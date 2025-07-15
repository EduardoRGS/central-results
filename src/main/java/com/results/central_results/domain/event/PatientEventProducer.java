package com.results.central_results.domain.event;

import com.results.central_results.domain.model.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatientEventProducer {

    private static final String PATIENT_EVENTS_TOPIC = "patient-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPatientCreated(Patient patient) {
        PatientCreatedEvent event = PatientCreatedEvent.builder()
                .patientId(patient.getId())
                .patientName(patient.getName())
                .patientEmail(patient.getEmail())
                .patientCpf(patient.getCpf())
                .createdAt(LocalDateTime.now())
                .build();

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(PATIENT_EVENTS_TOPIC, 
                patient.getId().toString(), event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Evento de paciente criado enviado com sucesso: {}", event.getPatientId());
            } else {
                log.error("Erro ao enviar evento de paciente criado: {}", ex.getMessage());
            }
        });
    }
} 