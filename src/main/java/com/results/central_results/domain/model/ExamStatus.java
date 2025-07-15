package com.results.central_results.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status do exame médico")
public enum ExamStatus {
    @Schema(description = "Pendente")
    PENDING,
    @Schema(description = "Em andamento")
    IN_PROGRESS,
    @Schema(description = "Concluído")
    COMPLETED,
    @Schema(description = "Cancelado")
    CANCELED
}
