package se.maje.scb_movements_backend.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import se.maje.scb_movements_backend.model.MovementRecord;

public interface MovementRepository
        extends ReactiveCrudRepository<MovementRecord, Long> {

    Flux<MovementRecord> findByRegionCode(String regionCode);

    Flux<MovementRecord> findByYear(Integer year);

    Flux<MovementRecord> findByRegionCodeAndYear(String regionCode, Integer year);
}
