package com.leocr.backendstackdemo.api.v1.controller;

import com.leocr.backendstackdemo.api.v1.dto.BrokerPageDto;
import com.leocr.backendstackdemo.common.service.BrokerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping(value = "/api/v1/reactive/rabbit", produces = APPLICATION_JSON_VALUE)
public class ReactiveRabbitController implements ReactiveBrokerController {

    private final BrokerService service;

    @Autowired
    public ReactiveRabbitController(@Qualifier("RabbitService") BrokerService service) {
        this.service = service;
    }

    @Override
    @Operation(summary = "List the messages produced and consumed in RabbitMQ using Spring WebFlux.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value that were produced and consumed by RabbitMQ using Spring WebFlux.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BrokerPageDto.class))
                    })
    })
    @GetMapping(value = "/messages", produces = "application/json")
    public @NotNull Flux<BrokerPageDto> list() {
        return Flux.just(new BrokerPageDto(service.list()));
    }
}
