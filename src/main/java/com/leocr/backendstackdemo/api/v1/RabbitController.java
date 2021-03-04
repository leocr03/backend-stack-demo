package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.dto.RabbitDto;
import com.leocr.backendstackdemo.api.v1.dto.RabbitPageDto;
import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/rabbit", produces = APPLICATION_JSON_VALUE)
public class RabbitController {

    private static final String KAFKA_VALUE_WAS_PRODUCED_DTO = "Kafka value was produced. dto={}";
    private static final String VALUE_PRODUCED_TO_RABBIT_MQ = "Value produced to RabbitMQ: ";

    private final RabbitService service;

    @Autowired
    public RabbitController(RabbitService service) {
        this.service = service;
    }

    @Operation(summary = "Produce value to RabbitMQ.", parameters = {
            @Parameter(name = "value", example = "11", description = "Integer number to be produced on RabbitMQ.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value produced to RabbitMQ.",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RabbitDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content)
    })
    @GetMapping(value = "/message/{value}")
    public ResponseEntity<RabbitDto> produce(@PathVariable Integer value) {
        ResponseEntity<RabbitDto> response;

        try {
            final String valueProduced = service.produce(value);
            final RabbitDto dto = new RabbitDto(valueProduced, VALUE_PRODUCED_TO_RABBIT_MQ + value);
            log.info(KAFKA_VALUE_WAS_PRODUCED_DTO, dto);
            response = new ResponseEntity<>(dto, HttpStatus.OK);
        } catch(IllegalArgumentException ex) {
            log.warn("Invalid parameter.", ex);
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @Operation(summary = "List the messages produced and consumed in RabbitMQ.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value that were produced and consumed by RabbitMQ.",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RabbitPageDto.class))
                    })
    })
    @GetMapping(value = "/messages")
    public ResponseEntity<RabbitPageDto> list() {
        return new ResponseEntity<>(new RabbitPageDto(service.list()), HttpStatus.OK);
    }
}
