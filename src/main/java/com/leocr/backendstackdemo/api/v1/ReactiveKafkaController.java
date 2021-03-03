package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.dto.KafkaPageDto;
import com.leocr.backendstackdemo.kafka.service.KafkaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reactive/kafka")
public class ReactiveKafkaController {

    private final KafkaService kafkaService;

    @Autowired
    public ReactiveKafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @Operation(summary = "List the messages produced and consumed in Kafka using Spring WebFlux.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value that were produced and consumed by Kafka using Spring WebFlux.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = KafkaPageDto.class))
                    })
    })
    @GetMapping(value = "/messages", produces = "application/json")
    public Mono<KafkaPageDto> list() {
        return Mono.just(new KafkaPageDto(kafkaService.list()));
    }
}
