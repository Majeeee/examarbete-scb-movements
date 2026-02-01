package se.maje.scb_movements_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import se.maje.scb_movements_backend.dto.MovementFilterDto;
import se.maje.scb_movements_backend.model.MovementRecord;
import se.maje.scb_movements_backend.repository.MovementRepository;
import se.maje.scb_movements_backend.repository.MovementRepositoryCustom;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final WebClient webClient; // Konfigurera i config
    private final MovementRepository movementRepository;
    private final MovementRepositoryCustom movementRepositoryCustom;
    private final RabbitPublishService rabbitPublishService;

    private static final String SCB_API_URL = "https://api.scb.se/OV0104/v1/doris/en/ssd/BE/BE0101/BE0101J/Flyttningar97";

    // Hämta och publicera SCB data
    public Flux<MovementRecord> fetchAndSaveScbData() {
        return webClient.post()
                .uri(SCB_API_URL)
                .bodyValue(buildRequestBody())
                .retrieve()
                .bodyToFlux(MovementRecord.class)
                .flatMap(record -> movementRepository.save(record)
                        .doOnSuccess(rabbitPublishService::publishMovementRecord));
    }

    private String buildRequestBody() {
        return """
                {
                  "query": [],
                  "response": {
                    "format": "json"
                  }
                }
                """;
    }

    // Filterad sökning
    public Flux<MovementRecord> filter(MovementFilterDto filters) {
        return movementRepositoryCustom.filter(filters);
    }
}
