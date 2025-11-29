package se.maje.scb_movements_backend.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import se.maje.scb_movements_backend.model.MovementRecord;

public interface MovementRepository extends JpaRepository<MovementRecord, Long>,
        JpaSpecificationExecutor<MovementRecord> {
}