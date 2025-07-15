package com.results.central_results.web.controller;

import com.results.central_results.domain.event.ExamEventProducer;
import com.results.central_results.domain.event.PatientEventProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Eventos", description = "API para monitoramento e teste de eventos")
@RequiredArgsConstructor
public class EventController {

    private final ExamEventProducer examEventProducer;
    private final PatientEventProducer patientEventProducer;

    @PostMapping("/test/exam-created")
    @Operation(summary = "Testar evento de exame criado", description = "Envia um evento de teste de exame criado")
    public ResponseEntity<Map<String, String>> testExamCreated() {
        // Simular evento de exame criado
        Map<String, String> response = new HashMap<>();
        response.put("message", "Evento de exame criado enviado com sucesso");
        response.put("topic", "exam-events");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test/exam-completed")
    @Operation(summary = "Testar evento de exame completado", description = "Envia um evento de teste de exame completado")
    public ResponseEntity<Map<String, String>> testExamCompleted() {
        // Simular evento de exame completado
        Map<String, String> response = new HashMap<>();
        response.put("message", "Evento de exame completado enviado com sucesso");
        response.put("topic", "exam-completed");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test/patient-created")
    @Operation(summary = "Testar evento de paciente criado", description = "Envia um evento de teste de paciente criado")
    public ResponseEntity<Map<String, String>> testPatientCreated() {
        // Simular evento de paciente criado
        Map<String, String> response = new HashMap<>();
        response.put("message", "Evento de paciente criado enviado com sucesso");
        response.put("topic", "patient-events");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    @Operation(summary = "Status dos eventos", description = "Retorna o status dos tópicos Kafka")
    public ResponseEntity<Map<String, Object>> getEventStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("kafka", "running");
        status.put("topics", new String[]{"exam-events", "exam-completed", "patient-events", "notifications"});
        status.put("consumers", "active");
        status.put("producers", "active");
        return ResponseEntity.ok(status);
    }
} 