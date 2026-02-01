package se.maje.scb_movements_backend.repository;

import reactor.core.publisher.Flux;
import se.maje.scb_movements_backend.dto.MovementFilterDto;
import se.maje.scb_movements_backend.model.MovementRecord;

public interface MovementRepositoryCustom {
    Flux<MovementRecord> filter(MovementFilterDto filters);
}
