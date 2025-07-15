package com.results.central_results.infrastructure.kafka;

import com.results.central_results.domain.event.ExamCompletedEvent;
import com.results.central_results.domain.event.ExamCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExamEventConsumer {

    @KafkaListener(topics = "exam-events", groupId = "central-results-group")
    public void handleExamCreated(ExamCreatedEvent event) {
        log.info("Recebido evento de exame criado: {}", event);
        
        // Aqui você pode implementar lógicas como:
        // - Enviar notificação para o laboratório
        // - Atualizar dashboard
        // - Registrar log de auditoria
        // - Integrar com sistemas externos
        
        log.info("Exame {} criado para paciente {} - Tipo: {}", 
                event.getExamId(), event.getPatientName(), event.getExamType());
    }

    @KafkaListener(topics = "exam-completed", groupId = "central-results-group")
    public void handleExamCompleted(ExamCompletedEvent event) {
        log.info("Recebido evento de exame completado: {}", event);
        
        // Aqui você pode implementar lógicas como:
        // - Enviar notificação por email para o paciente
        // - Atualizar status no sistema
        // - Gerar relatório
        // - Integrar com sistemas de notificação
        
        log.info("Exame {} completado para paciente {} - Resultado disponível", 
                event.getExamId(), event.getPatientName());
        
        // Simular envio de notificação por email
        sendEmailNotification(event);
    }

    private void sendEmailNotification(ExamCompletedEvent event) {
        String subject = "Resultado do Exame Disponível";
        String message = String.format(
                "Olá %s,\n\n" +
                "O resultado do seu exame %s está disponível.\n" +
                "Acesse o sistema para visualizar o resultado completo.\n\n" +
                "Atenciosamente,\nCentral Results",
                event.getPatientName(),
                event.getExamType()
        );
        
        log.info("Notificação por email enviada para: {}", event.getPatientEmail());
        log.info("Assunto: {}", subject);
        log.info("Mensagem: {}", message);
    }
} 