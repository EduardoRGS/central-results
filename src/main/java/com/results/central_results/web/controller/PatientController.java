package com.results.central_results.controller;

import com.results.central_results.dto.PatientDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.results.central_results.application.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping
    public PatientDTO create(@Valid @RequestBody PatientDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<PatientDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PatientDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public PatientDTO update(@PathVariable Long id, @Valid @RequestBody PatientDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
