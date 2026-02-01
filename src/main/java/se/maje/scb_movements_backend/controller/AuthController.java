package se.maje.scb_movements_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import se.maje.scb_movements_backend.dto.AuthRequest;
import se.maje.scb_movements_backend.dto.AuthResponse;
import se.maje.scb_movements_backend.dto.RegisterRequest;
import se.maje.scb_movements_backend.model.User;
import se.maje.scb_movements_backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // LOGIN
    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody AuthRequest request) {
        return authService.login(request.getEmail(), request.getPassword())
                .map(AuthResponse::new);
    }

    // REGISTER
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> register(@RequestBody RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .enabled(false) // kräver aktivering
                .build();

        return authService.register(user);
    }
}
