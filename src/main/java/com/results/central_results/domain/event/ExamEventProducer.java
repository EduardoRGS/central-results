package com.results.central_results.domain.event;

import com.results.central_results.domain.model.Exam;
import com.results.central_results.domain.model.ExamStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExamEventProducer {

    private static final String EXAM_EVENTS_TOPIC = "exam-events";
    private static final String EXAM_COMPLETED_TOPIC = "exam-completed";
    private static final String NOTIFICATION_TOPIC = "notifications";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendExamCreated(Exam exam) {
        ExamCreatedEvent event = ExamCreatedEvent.builder()
                .examId(exam.getId())
                .patientId(exam.getPatient().getId())
                .patientName(exam.getPatient().getName())
                .examType(exam.getType())
                .status(exam.getStatus())
                .createdAt(exam.getCreatedAt())
                .build();

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(EXAM_EVENTS_TOPIC, 
                exam.getId().toString(), event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Evento de exame criado enviado com sucesso: {}", event.getExamId());
            } else {
                log.error("Erro ao enviar evento de exame criado: {}", ex.getMessage());
            }
        });
    }

    public void sendExamCompleted(Exam exam) {
        ExamCompletedEvent event = ExamCompletedEvent.builder()
                .examId(exam.getId())
                .patientId(exam.getPatient().getId())
                .patientName(exam.getPatient().getName())
                .patientEmail(exam.getPatient().getEmail())
                .examType(exam.getType())
                .result(exam.getResult())
                .completedAt(exam.getCompletedAt())
                .build();

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(EXAM_COMPLETED_TOPIC, 
                exam.getId().toString(), event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Evento de exame completado enviado com sucesso: {}", event.getExamId());
            } else {
                log.error("Erro ao enviar evento de exame completado: {}", ex.getMessage());
            }
        });
    }

    public void sendNotification(String patientEmail, String message) {
        NotificationEvent notification = NotificationEvent.builder()
                .email(patientEmail)
                .message(message)
                .type("EXAM_RESULT")
                .build();

        kafkaTemplate.send(NOTIFICATION_TOPIC, patientEmail, notification);
    }
}
