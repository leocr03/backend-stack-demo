package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.dto.KafkaDto;
import com.leocr.backendstackdemo.api.v1.dto.KafkaPageDto;
import com.leocr.backendstackdemo.kafka.service.KafkaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/api/v1/kafka", produces = APPLICATION_JSON_VALUE)
public class KafkaController {

    private static final String KAFKA_VALUE_WAS_PRODUCED_DTO = "Kafka value was produced. dto={}";
    private static final String VALUE_PRODUCED_TO_KAFKA = "Value produced to Kafka: ";

    private final KafkaService service;

    @Autowired
    public KafkaController(KafkaService service) {
        this.service = service;
    }

    @Operation(summary = "Produce value to Kafka.", parameters = {
            @Parameter(name = "value", example = "1", description = "Integer number to be produced on Kafka.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value produced to Kafka.",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = KafkaDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content)
    })
    @GetMapping(value = "/message/{value}") // it Kept as GET in order to facilitate tests
    public ResponseEntity<KafkaDto> produce(
            @PathVariable(name = "value") @Range(min = 1, max = 99999) Integer value) {
        ResponseEntity<KafkaDto> response;

        try {
            final String valueProduced = service.produce(value);
            final KafkaDto dto = new KafkaDto(valueProduced, VALUE_PRODUCED_TO_KAFKA + value);
            log.info(KAFKA_VALUE_WAS_PRODUCED_DTO, dto);
            response = new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid parameter.", ex);
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @Operation(summary = "List the messages produced and consumed in Kafka.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value that were produced and consumed by Kafka.",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = KafkaPageDto.class))
                    })
    })
    @GetMapping(value = "/messages")
    public ResponseEntity<KafkaPageDto> list() {
        return new ResponseEntity<>(new KafkaPageDto(service.list()), HttpStatus.OK);
    }
}
