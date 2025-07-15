package com.results.central_results.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Central Results API")
                        .description("API para gestão de resultados de exames médicos. " +
                                   "Sistema completo para laboratórios médicos gerenciarem pacientes e seus exames.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Central Results Team")
                                .email("suporte@centralresults.com")
                                .url("https://centralresults.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor de Desenvolvimento"),
                        new Server().url("https://api.centralresults.com").description("Servidor de Produção")
                ));
    }
} 