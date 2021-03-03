package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.dto.RabbitPageDto;
import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/reactive/rabbit", produces = APPLICATION_JSON_VALUE)
public class ReactiveRabbitController {

    private final RabbitService rabbitService;

    @Autowired
    public ReactiveRabbitController(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    @Operation(summary = "List the messages produced and consumed in RabbitMQ using Spring WebFlux.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value that were produced and consumed by RabbitMQ using Spring WebFlux.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RabbitPageDto.class))
                    })
    })
    @GetMapping(value = "/messages", produces = "application/json")
    public Flux<RabbitPageDto> list() {
        return Flux.just(new RabbitPageDto(rabbitService.list()));
    }
}
