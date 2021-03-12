package com.leocr.backendstackdemo.api.v1;

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

@Validated
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/rabbit", produces = APPLICATION_JSON_VALUE)
public class RabbitController implements BrokerController {

    private static final String RABBIT_VALUE_WAS_PRODUCED_DTO = "Rabbit value was produced. dto={}";
    private static final String VALUE_PRODUCED_TO_RABBIT_MQ = "Value produced to RabbitMQ: ";

    private final BrokerService service;

    @Autowired
    public RabbitController(@Qualifier("RabbitService") BrokerService service) {
        this.service = service;
    }

    @Override
    @Operation(summary = "Produce value to RabbitMQ.", parameters = {
            @Parameter(name = "value", example = "11", description = "Integer number to be produced on RabbitMQ.")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value produced to RabbitMQ.",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BrokerDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content)
    })
    @GetMapping(value = "/message/{value}")
    public @NotNull ResponseEntity<BrokerDto> produce(@PathVariable @Range(min = 1, max = 99999) @NotNull Integer value) {
        final String valueProduced = service.produce(value);
        final BrokerDto dto = new BrokerDto(valueProduced, VALUE_PRODUCED_TO_RABBIT_MQ + value);
        log.info(RABBIT_VALUE_WAS_PRODUCED_DTO, dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    @Operation(summary = "List the messages produced and consumed in RabbitMQ.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value that were produced and consumed by RabbitMQ.",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BrokerPageDto.class))
                    })
    })
    @GetMapping(value = "/messages")
    public @NotNull ResponseEntity<BrokerPageDto> list() {
        return new ResponseEntity<>(new BrokerPageDto(service.list()), HttpStatus.OK);
    }
}
