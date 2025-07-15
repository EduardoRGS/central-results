package com.results.central_results.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.time.LocalDate;

public record PatientDTO(
        Long id,

        @NotBlank
        String name,

        @NotBlank @Email
        String email,

        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        @Past
        LocalDate birthDate
) implements Serializable {}

