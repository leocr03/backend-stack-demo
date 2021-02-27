package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.model.KafkaResponse;
import com.leocr.backendstackdemo.kafka.service.KafkaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaController {

    private final KafkaService kafkaService;

    @Autowired
    public KafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @Operation(summary = "Produce value to Kafka.", parameters = {
            @Parameter(name = "value", example = "1", description = "Integer number to be produced on Kafka.")
    })
    @GetMapping(value = "/message/{value}")
    public void produce(@PathVariable
                                    Integer value) {
        kafkaService.produce(value);
    }

    @Operation(summary = "List the messages produced and consumed in Kafka.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value that were produced and consumed by Kafka.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))
                    })
    })
    @GetMapping(value = "/messages", produces = "application/json")
    public KafkaResponse list() {
        return new KafkaResponse(kafkaService.list());
    }
}
