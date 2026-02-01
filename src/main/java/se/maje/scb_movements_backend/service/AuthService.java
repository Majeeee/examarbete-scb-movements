package se.maje.scb_movements_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import se.maje.scb_movements_backend.mail.EmailService;
import se.maje.scb_movements_backend.model.User;
import se.maje.scb_movements_backend.repository.UserRepository;
import se.maje.scb_movements_backend.config.JwtUtil;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RabbitPublishService rabbitPublishService;
//    private final EmailService emailService;

    // LOGIN
    public Mono<String> login(String email, String password) {

        return userRepository.findByEmail(email)
                .filter(User::isEnabled)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .flatMap(this::generateJwt)
                .doOnNext(token -> {
                    // Skicka event via RabbitMQ
                    User userEvent = User.builder()
                            .email(email)
                            .enabled(true)
                            .build();

                    rabbitPublishService.publishUserLoggedIn(userEvent);
                });
    }


    // REGISTER
    public Mono<User> register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false); // kräver aktivering

        return userRepository.save(user)
                .doOnSuccess(rabbitPublishService::publishUserRegistered);
    }

    // Genererar JWT baserat på User.roles
    private Mono<String> generateJwt(User user) {

        Collection<String> roles = user.getRoles().stream()
                .map(r -> "ROLE_" + r.getName())
                .toList();

        return Mono.just(jwtUtil.generateToken(user.getEmail(), roles));
    }
}
