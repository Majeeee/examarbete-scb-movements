package se.maje.scb_movements_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import se.maje.scb_movements_backend.dto.ScbResponseDto;
import se.maje.scb_movements_backend.model.MovementRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScbService {

    private final WebClient webClient;

    private static final String SCB_API_URL =
            "https://api.scb.se/OV0104/v1/doris/en/ssd/BE/BE0101/BE0101J/Flyttningar97";

    public Flux<MovementRecord> fetchMovements() {

        return webClient.post()
                .uri(SCB_API_URL)
                .bodyValue(buildRequestBody())
                .retrieve()
                .bodyToMono(ScbResponseDto.class)
                .flatMapMany(this::mapToMovements);
    }

    private Flux<MovementRecord> mapToMovements(ScbResponseDto response) {

        return Flux.fromIterable(response.getData())
                .map(row -> {
                    MovementRecord rec = new MovementRecord();

                    rec.setRegionCode(row.getKey().get(0));
                    rec.setYear(Integer.valueOf(row.getKey().get(1)));
                    rec.setSex(row.getKey().get(2));
                    rec.setAge(row.getKey().get(3));

                    rec.setMoveType("IN"); // eller baserat på query
                    rec.setInflow(Integer.valueOf(row.getValues().get(0)));
                    rec.setDate(LocalDate.of(rec.getYear(), 1, 1));

                    return rec;
                });
    }

    private Map<String, Object> buildRequestBody() {

        return Map.of(
                "query", List.of(
                        Map.of(
                                "code", "Region",
                                "selection", Map.of(
                                        "filter", "all",
                                        "values", List.of("*")
                                )
                        ),
                        Map.of(
                                "code", "Tid",
                                "selection", Map.of(
                                        "filter", "all",
                                        "values", List.of("*")
                                )
                        )
                ),
                "response", Map.of("format", "json")
        );
    }
}
