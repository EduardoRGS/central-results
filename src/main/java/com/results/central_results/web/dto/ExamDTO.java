package com.results.central_results.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.results.central_results.domain.model.ExamStatus;

import java.time.LocalDateTime;

public record ExamDTO(
        Long id,

        @NotNull Long patientId,

        @NotBlank String type,

        String result,

        ExamStatus status,

        LocalDateTime createdAt,

        LocalDateTime completedAt
) {}
