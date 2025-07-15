package com.results.central_results.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para paciente")
public class PatientDTO {
        @Schema(description = "ID único do paciente", example = "1")
        private Long id;

        @NotBlank
        @Schema(description = "Nome completo do paciente", example = "João Silva", required = true)
        private String name;

        @NotBlank @Email
        @Schema(description = "Email do paciente", example = "joao.silva@email.com", required = true)
        private String email;

        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        @Schema(description = "CPF do paciente", example = "12345678901")
        private String cpf;

        @Past
        @Schema(description = "Data de nascimento", example = "1990-01-01")
        private LocalDate birthDate;

        @Schema(description = "Telefone do paciente", example = "(11) 99999-9999")
        private String phone;
}

