package com.results.central_results.infrastructure.config;

import com.results.central_results.domain.model.Role;
import com.results.central_results.domain.model.User;
import com.results.central_results.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Criar usuário admin se não existir
        if (!userRepository.existsByEmail("admin@centralresults.com")) {
            User admin = User.builder()
                    .name("Administrador")
                    .email("admin@centralresults.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();
            userRepository.save(admin);
        }

        // Criar usuário médico se não existir
        if (!userRepository.existsByEmail("doctor@centralresults.com")) {
            User doctor = User.builder()
                    .name("Dr. João Silva")
                    .email("doctor@centralresults.com")
                    .password(passwordEncoder.encode("doctor123"))
                    .role(Role.DOCTOR)
                    .enabled(true)
                    .build();
            userRepository.save(doctor);
        }

        // Criar usuário técnico de laboratório se não existir
        if (!userRepository.existsByEmail("lab@centralresults.com")) {
            User labTech = User.builder()
                    .name("Maria Santos")
                    .email("lab@centralresults.com")
                    .password(passwordEncoder.encode("lab123"))
                    .role(Role.LAB_TECHNICIAN)
                    .enabled(true)
                    .build();
            userRepository.save(labTech);
        }

        // Criar usuário recepcionista se não existir
        if (!userRepository.existsByEmail("reception@centralresults.com")) {
            User receptionist = User.builder()
                    .name("Ana Costa")
                    .email("reception@centralresults.com")
                    .password(passwordEncoder.encode("reception123"))
                    .role(Role.RECEPTIONIST)
                    .enabled(true)
                    .build();
            userRepository.save(receptionist);
        }
    }
} 