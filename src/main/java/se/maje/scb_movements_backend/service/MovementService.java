package se.maje.scb_movements_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.maje.scb_movements_backend.model.MovementRecord;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScbService {

    private final WebClient webClient; // konfigurera bean i config
    private final RabbitPublishService rabbitPublishService;

    private static final String SCB_API_URL = "https://api.scb.se/OV0104/v1/doris/en/ssd/BE/BE0101/BE0101J/Flyttningar97";

    /** Hämtar data från SCB och publicerar event */
    public Mono<MovementRecord> fetchScbData() {
        return webClient.post()
                .uri(SCB_API_URL)
                .bodyValue(buildRequestBody()) // exempel: JSON-body för SCB API
                .retrieve()
                .bodyToMono(MovementRecord.class)
                .doOnNext(rabbitPublishService::publishMovementRecord);
    }

    /** Skapa request body för SCB API */
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
}
