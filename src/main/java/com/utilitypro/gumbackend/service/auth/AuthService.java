package com.utilitypro.gumbackend.service.auth;

import com.utilitypro.gumbackend.domain.dto.AuthRequest;
import com.utilitypro.gumbackend.domain.dto.AuthResponse;
import com.utilitypro.gumbackend.domain.entity.AppUser;
import com.utilitypro.gumbackend.domain.enums.Role;
import com.utilitypro.gumbackend.repository.AppUserRepository;
import com.utilitypro.gumbackend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(AuthRequest request) {
        AppUser user = AppUser.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        appUserRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        AppUser user = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        return new AuthResponse(jwtService.generateToken(user));
    }
}
