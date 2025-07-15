package com.results.central_results.infrastructure.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    private final Counter examCreatedCounter;
    private final Counter examCompletedCounter;
    private final Counter patientCreatedCounter;

    public CustomMetrics(MeterRegistry meterRegistry) {
        this.examCreatedCounter = Counter.builder("exams.created")
                .description("Número de exames criados")
                .register(meterRegistry);
        
        this.examCompletedCounter = Counter.builder("exams.completed")
                .description("Número de exames completados")
                .register(meterRegistry);
        
        this.patientCreatedCounter = Counter.builder("patients.created")
                .description("Número de pacientes criados")
                .register(meterRegistry);
    }

    public void incrementExamCreated() {
        examCreatedCounter.increment();
    }

    public void incrementExamCompleted() {
        examCompletedCounter.increment();
    }

    public void incrementPatientCreated() {
        patientCreatedCounter.increment();
    }
} 