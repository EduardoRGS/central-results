package com.results.central_results.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.results.central_results.domain.model.ExamStatus;
import com.results.central_results.web.dto.ExamDTO;
import com.results.central_results.web.dto.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class ExamIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldCreateExamSuccessfully() throws Exception {
        // Given
        PatientDTO patientDTO = PatientDTO.builder()
                .name("Maria Silva")
                .email("maria@email.com")
                .cpf("12345678901")
                .build();

        ExamDTO examDTO = ExamDTO.builder()
                .patientId(1L)
                .type("Hemograma")
                .status(ExamStatus.PENDING)
                .build();

        // When & Then
        mockMvc.perform(post("/api/exams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("Hemograma"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldReturnBadRequestWhenExamDataIsInvalid() throws Exception {
        // Given
        ExamDTO invalidExamDTO = ExamDTO.builder()
                .type("") // Campo obrigatório vazio
                .build();

        // When & Then
        mockMvc.perform(post("/api/exams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidExamDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenExamDoesNotExist() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/exams/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllExams() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/exams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
} 