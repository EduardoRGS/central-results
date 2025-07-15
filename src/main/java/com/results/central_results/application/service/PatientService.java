package com.results.central_results.application.service;

import com.results.central_results.web.dto.PatientDTO;
import com.results.central_results.domain.exception.PatientNotFoundException;
import com.results.central_results.domain.model.Patient;
import com.results.central_results.domain.event.PatientEventProducer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.results.central_results.domain.repository.PatientRepository;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repository;
    private final PatientEventProducer eventProducer;

    public PatientService(PatientRepository repository, PatientEventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    public PatientDTO create(PatientDTO dto) {
        Patient patient = new Patient();
        mapFromDTO(dto, patient);
        Patient savedPatient = repository.save(patient);
        eventProducer.sendPatientCreated(savedPatient);
        return mapToDTO(savedPatient);
    }

    public List<PatientDTO> findAll() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Cacheable(value = "patients", key = "#id")
    public PatientDTO findById(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
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
        return PatientDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .email(p.getEmail())
                .cpf(p.getCpf())
                .birthDate(p.getBirthDate())
                .phone(p.getPhone())
                .build();
    }

    private void mapFromDTO(PatientDTO dto, Patient p) {
        p.setName(dto.getName());
        p.setEmail(dto.getEmail());
        p.setCpf(dto.getCpf());
        p.setBirthDate(dto.getBirthDate());
        p.setPhone(dto.getPhone());
    }
}
