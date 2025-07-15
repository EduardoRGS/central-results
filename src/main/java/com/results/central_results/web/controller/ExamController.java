package com.results.central_results.controller;

import com.results.central_results.dto.ExamDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.results.central_results.application.service.ExamService;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService service;

    public ExamController(ExamService service) {
        this.service = service;
    }

    @PostMapping
    public ExamDTO create(@Valid @RequestBody ExamDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<ExamDTO> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ExamDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public ExamDTO update(@PathVariable Long id, @Valid @RequestBody ExamDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}