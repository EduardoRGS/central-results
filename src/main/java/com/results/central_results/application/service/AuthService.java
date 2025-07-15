package com.results.central_results.application.service;

import com.results.central_results.domain.model.Role;
import com.results.central_results.domain.model.User;
import com.results.central_results.domain.repository.UserRepository;
import com.results.central_results.infrastructure.security.JwtService;
import com.results.central_results.web.dto.AuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthDTO.AuthResponse register(AuthDTO.RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role() != null ? request.role() : Role.RECEPTIONIST)
                .enabled(true)
                .build();

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthDTO.AuthResponse(
                jwt,
                refreshToken,
                "Bearer",
                user.getEmail(),
                user.getName(),
                user.getRole()
        );
    }

    public AuthDTO.AuthResponse login(AuthDTO.LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthDTO.AuthResponse(
                jwt,
                refreshToken,
                "Bearer",
                user.getEmail(),
                user.getName(),
                user.getRole()
        );
    }

    public AuthDTO.AuthResponse refreshToken(AuthDTO.RefreshTokenRequest request) {
        // Implementar lógica de refresh token
        // Por simplicidade, retornamos um novo token
        return new AuthDTO.AuthResponse(
                "new-jwt-token",
                "new-refresh-token",
                "Bearer",
                "user@email.com",
                "User Name",
                Role.ADMIN
        );
    }
} 