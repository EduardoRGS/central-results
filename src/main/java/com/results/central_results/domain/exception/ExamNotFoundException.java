package com.results.central_results.domain.exception;

public class ExamNotFoundException extends RuntimeException {
    public ExamNotFoundException(String message) {
        super(message);
    }
    
    public ExamNotFoundException(Long id) {
        super("Exame com ID " + id + " não encontrado");
    }
} 