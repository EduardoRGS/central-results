package com.results.central_results.event;

import com.results.central_results.dto.ExamDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExamEventProducer {

    private static final String TOPIC = "exam-events";

    private final KafkaTemplate<String, ExamDTO> kafkaTemplate;

    public ExamEventProducer(KafkaTemplate<String, ExamDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendExamCreated(ExamDTO examDTO) {
        kafkaTemplate.send(TOPIC, examDTO.id() != null ? examDTO.id().toString() : "new", examDTO);
    }
}
