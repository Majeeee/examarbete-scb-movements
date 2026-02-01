package se.maje.scb_movements_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import se.maje.scb_movements_backend.dto.MovementFilterDto;
import se.maje.scb_movements_backend.model.MovementRecord;
import se.maje.scb_movements_backend.service.MovementService;

@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    // Hämtar data direkt från SCB och sparar i DB
    @PostMapping("/fetch")
    public Flux<MovementRecord> fetchScb() {
        return movementService.fetchAndSaveScbData();
    }

    // Filterad sökning
    @PostMapping("/filter")
    public Flux<MovementRecord> filter(@RequestBody MovementFilterDto filters) {
        return movementService.filter(filters);
    }
}
