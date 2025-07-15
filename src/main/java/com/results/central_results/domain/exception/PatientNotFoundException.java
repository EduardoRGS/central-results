package com.results.central_results.domain.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        super(message);
    }
    
    public PatientNotFoundException(Long id) {
        super("Paciente com ID " + id + " não encontrado");
    }
} 