package se.maje.scb_movements_backend.config;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import se.maje.scb_movements_backend.model.Permission;
import se.maje.scb_movements_backend.model.Role;
import se.maje.scb_movements_backend.repository.PermissionRepository;
import se.maje.scb_movements_backend.repository.RoleRepository;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final Flyway flyway;

    @Override
    public void run(String... args) {
        flyway.migrate(); // kör migrationerna först
        // Kör Flyway migrering på en scheduler som tillåter blockerande kod
        Mono.fromRunnable(() -> {
                    System.out.println("Starting Flyway migrations...");
                    flyway.migrate();
                    System.out.println("Flyway migrations completed.");
                }).subscribeOn(Schedulers.boundedElastic()) // Blockerande körs på separat tråd
                .thenMany(initializeData()) // Efter migrering, kör datainitialisering
                .subscribe(
                        role -> System.out.println("Role saved: " + role.getName()),
                        error -> System.err.println("Error initializing data: " + error),
                        () -> System.out.println("Data initialization completed.")
                );
    }

    private Flux<Role> initializeData() {
        // Skapa permissions
        Permission read = new Permission(null, "READ_PRIVILEGE");
        Permission write = new Permission(null, "WRITE_PRIVILEGE");

        return permissionRepository.saveAll(Flux.just(read, write))
                .collectList()
                .flatMapMany(perms -> {
                    if (perms.isEmpty()) return Flux.empty();

                    Role admin = Role.builder()
                            .name("ROLE_ADMIN")
                            .permissions(Set.copyOf(perms))
                            .build();

                    Role userRole = Role.builder()
                            .name("ROLE_USER")
                            .permissions(Set.of(read))
                            .build();

                    return roleRepository.saveAll(Flux.just(admin, userRole));
                });
    }
}
