package com.results.central_results.infrastructure.kafka;

import com.results.central_results.domain.event.ExamCreatedEvent;
import com.results.central_results.domain.event.PatientCreatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"exam-events", "exam-completed", "patient-events", "notifications"})
@ActiveProfiles("test")
class KafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void shouldSendExamCreatedEvent() {
        ExamCreatedEvent event = ExamCreatedEvent.builder()
                .examId(1L)
                .patientId(1L)
                .patientName("João Silva")
                .examType("Hemograma")
                .createdAt(LocalDateTime.now())
                .build();

        assertDoesNotThrow(() -> {
            kafkaTemplate.send("exam-events", "1", event);
        });
    }

    @Test
    void shouldSendPatientCreatedEvent() {
        PatientCreatedEvent event = PatientCreatedEvent.builder()
                .patientId(1L)
                .patientName("Maria Santos")
                .patientEmail("maria@email.com")
                .patientCpf("12345678901")
                .createdAt(LocalDateTime.now())
                .build();

        assertDoesNotThrow(() -> {
            kafkaTemplate.send("patient-events", "1", event);
        });
    }
} 