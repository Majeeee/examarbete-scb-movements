package se.maje.scb_movements_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.maje.scb_movements_backend.dto.UserDto;
import se.maje.scb_movements_backend.model.User;
import se.maje.scb_movements_backend.model.Role;
import se.maje.scb_movements_backend.model.Permission;
import se.maje.scb_movements_backend.repository.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .authorities(user.getRoles().stream()
                                .flatMap(r -> r.getPermissions().stream())
                                .map(Permission::getName)
                                .toArray(String[]::new))
                        .build());
    }

    public Mono<User> register(User user, Role defaultRole) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRoles(Set.of(defaultRole));
        return userRepository.save(user);
    }

    public Mono<UserDetails> authenticate(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")))
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .authorities(user.getRoles().stream()
                                .flatMap(r -> r.getPermissions().stream())
                                .map(Permission::getName)
                                .toArray(String[]::new))
                        .build());
    }

    public Flux<UserDto> getAllUsersDto() {
        return userRepository.findAll()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getEmail(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getEnabled()
                ));
    }

    public Mono<UserDto> getCurrentUserDto() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName()) // Get logged-in email
                .flatMap(userRepository::findByEmail)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getEmail(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getEnabled()
                ));
    }

    public Mono<Void> deleteUser(Long id) {
        return userRepository.deleteById(id);
}
}
