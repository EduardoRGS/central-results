package com.results.central_results.infrastructure.persistence;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.results.central_results.domain.repository")
@EnableJpaAuditing
@EnableTransactionManagement
public class JpaConfig {
    // Configurações específicas de JPA podem ser adicionadas aqui
} 