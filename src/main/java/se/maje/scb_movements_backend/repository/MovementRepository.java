package se.maje.scb_movements_backend.service;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import se.maje.scb_movements_backend.model.MovementRecord;

public interface MovementRepository extends ReactiveCrudRepository<MovementRecord, Long>, MovementRepositoryCustom {
}
