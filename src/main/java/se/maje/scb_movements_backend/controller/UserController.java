package se.maje.scb_movements_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.maje.scb_movements_backend.dto.UserDto;
import se.maje.scb_movements_backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Endast ADMIN kan se alla användare
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Flux<UserDto> getAllUsers() {
        return userService.getAllUsersDto();
    }
    // Endast ADMIN kan radera användare
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public Mono<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                .thenReturn("User removed");
    }

    // Användaren kan se sin egen profil
    @GetMapping("/me")
    public Mono<UserDto> getProfile() {
        return userService.getCurrentUserDto();
    }
}
