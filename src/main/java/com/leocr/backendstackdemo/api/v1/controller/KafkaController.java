package com.leocr.backendstackdemo.api.v1.controller;

import com.leocr.backendstackdemo.api.v1.dto.BrokerDto;
import com.leocr.backendstackdemo.api.v1.dto.BrokerPageDto;
import com.leocr.backendstackdemo.common.service.BrokerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class KafkaController implements BrokerController {

    private static final String KAFKA_VALUE_WAS_PRODUCED_DTO = "Kafka value was produced. dto={}";
    public static final String VALUE_PRODUCED_TO_KAFKA = "Value produced to Kafka: %s";

    private final BrokerService service;

    @Autowired
    public KafkaController(@Qualifier("KafkaService") BrokerService service) {
        this.service = service;
    }

    @Override
    @Operation(summary = "Produce value to Kafka.",
            parameters = {@Parameter(name = "value", example = "1",
                    description = "Integer number to be produced on Kafka.")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value produced to Kafka.",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BrokerDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content)
    })
    @GetMapping(value = "/message/{value}") // it Kept as GET in order to facilitate tests
    public @NotNull ResponseEntity<BrokerDto> produce(
            @PathVariable(name = "value") @Range(min = 1, max = 99999) @NotNull Integer value) {
        final String valueProduced = service.produce(value);
        final BrokerDto dto = new BrokerDto(valueProduced, String.format(VALUE_PRODUCED_TO_KAFKA, value));
        log.info(KAFKA_VALUE_WAS_PRODUCED_DTO, dto);
        log.info("value={}", dto.getValue());
        log.info("message={}", dto.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    @Operation(summary = "List the messages produced and consumed in Kafka.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value that were produced and consumed by Kafka.",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BrokerPageDto.class))})
    })
    @GetMapping(value = "/messages")
    public @NotNull ResponseEntity<BrokerPageDto> list() {
        return new ResponseEntity<>(new BrokerPageDto(service.list()), HttpStatus.OK);
    }
}
