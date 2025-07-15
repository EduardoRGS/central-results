package com.results.central_results.infrastructure.kafka;

import com.results.central_results.domain.event.PatientCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatientEventConsumer {

    @KafkaListener(topics = "patient-events", groupId = "central-results-group")
    public void handlePatientCreated(PatientCreatedEvent event) {
        log.info("Recebido evento de paciente criado: {}", event);
        
        // Aqui você pode implementar lógicas como:
        // - Enviar email de boas-vindas
        // - Criar perfil no sistema
        // - Registrar log de auditoria
        // - Integrar com sistemas externos
        
        log.info("Paciente {} registrado com sucesso - Email: {}", 
                event.getPatientName(), event.getPatientEmail());
        
        // Simular envio de email de boas-vindas
        sendWelcomeEmail(event);
    }

    private void sendWelcomeEmail(PatientCreatedEvent event) {
        String subject = "Bem-vindo ao Central Results";
        String message = String.format(
                "Olá %s,\n\n" +
                "Seja bem-vindo ao Central Results!\n" +
                "Seu cadastro foi realizado com sucesso.\n\n" +
                "Dados do cadastro:\n" +
                "- Nome: %s\n" +
                "- Email: %s\n" +
                "- CPF: %s\n\n" +
                "Em caso de dúvidas, entre em contato conosco.\n\n" +
                "Atenciosamente,\nCentral Results",
                event.getPatientName(),
                event.getPatientName(),
                event.getPatientEmail(),
                event.getPatientCpf()
        );
        
        log.info("Email de boas-vindas enviado para: {}", event.getPatientEmail());
        log.info("Assunto: {}", subject);
        log.info("Mensagem: {}", message);
    }
} 