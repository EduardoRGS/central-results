package com.results.central_results.web.dto;

import com.results.central_results.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDTO {

    public record LoginRequest(
            @NotBlank(message = "Email é obrigatório")
            @Email(message = "Email deve ser válido")
            String email,
            
            @NotBlank(message = "Senha é obrigatória")
            String password
    ) {}

    public record RegisterRequest(
            @NotBlank(message = "Nome é obrigatório")
            @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
            String name,
            
            @NotBlank(message = "Email é obrigatório")
            @Email(message = "Email deve ser válido")
            String email,
            
            @NotBlank(message = "Senha é obrigatória")
            @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
            String password,
            
            Role role
    ) {}

    public record AuthResponse(
            String token,
            String refreshToken,
            String type,
            String email,
            String name,
            Role role
    ) {}

    public record RefreshTokenRequest(
            @NotBlank(message = "Refresh token é obrigatório")
            String refreshToken
    ) {}
} 