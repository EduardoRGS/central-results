package com.results.central_results.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import com.results.central_results.domain.model.ExamStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para exame médico")
public class ExamDTO{
    @Schema(description = "ID único do exame", example = "1")
    private Long id;

    @NotNull(message = "O ID do paciente é obrigatório")
    @Schema(description = "ID do paciente", example = "1", required = true)
    private Long patientId;

    @NotBlank(message = "O tipo do exame é obrigatório")
    @Size(min = 2, max = 100, message = "O tipo do exame deve ter entre 2 e 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s\\d]+$", message = "O tipo do exame deve conter apenas letras, números e espaços")
    @Schema(description = "Tipo do exame", example = "Hemograma", required = true)
    private String type;

    @Size(max = 1000, message = "O resultado do exame deve ter no máximo 1000 caracteres")
    @Schema(description = "Resultado do exame", example = "Normal")
    private String result;

    @Schema(description = "Status do exame", example = "COMPLETED")
    private ExamStatus status;

    @Schema(description = "Data de criação do exame")
    private LocalDateTime createdAt;

    @Schema(description = "Data de conclusão do exame")
    private LocalDateTime completedAt;
}
