package se.maje.scb_movements_backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
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

    @Override
    public void run(String... args) {
        Permission read = new Permission(null, "READ_PRIVILEGE");
        Permission write = new Permission(null, "WRITE_PRIVILEGE");

        permissionRepository.saveAll(Flux.just(read, write))
                .collectList()
                .flatMapMany(perms -> {
                    Role admin = new Role(null, "ROLE_ADMIN", Set.copyOf(perms));
                    Role user = new Role(null, "ROLE_USER", Set.of(read));
                    return roleRepository.saveAll(Flux.just(admin, user));
                })
                .subscribe();
    }
}
