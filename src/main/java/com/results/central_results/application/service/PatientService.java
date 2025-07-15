package com.results.central_results.service;

import com.results.central_results.dto.PatientDTO;
import com.results.central_results.domain.model.Patient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.results.central_results.domain.repository.PatientRepository;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public PatientDTO create(PatientDTO dto) {
        Patient patient = new Patient();
        mapFromDTO(dto, patient);
        return mapToDTO(repository.save(patient));
    }

    public List<PatientDTO> findAll() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Cacheable(value = "patients", key = "#id")
    public PatientDTO findById(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return mapToDTO(patient);
    }

    @Cacheable(value = "patients", key = "#id")
    public PatientDTO update(Long id, PatientDTO dto) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        mapFromDTO(dto, patient);
        return mapToDTO(repository.save(patient));
    }

    @Cacheable(value = "patients", key = "#id")
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private PatientDTO mapToDTO(Patient p) {
        return new PatientDTO(p.getId(), p.getName(), p.getEmail(), p.getCpf(), p.getBirthDate());
    }

    private void mapFromDTO(PatientDTO dto, Patient p) {
        p.setName(dto.name());
        p.setEmail(dto.email());
        p.setCpf(dto.cpf());
        p.setBirthDate(dto.birthDate());
    }
}
