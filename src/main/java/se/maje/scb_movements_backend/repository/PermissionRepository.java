package se.maje.scb_movements_backend.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import se.maje.scb_movements_backend.model.Permission;

public interface PermissionRepository
        extends ReactiveCrudRepository<Permission, Long> {
}
