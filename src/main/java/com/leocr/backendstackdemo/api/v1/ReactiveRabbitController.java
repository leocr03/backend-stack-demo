package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.model.RabbitResponse;
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

@RestController
@RequestMapping("/api/v1/reactive/rabbit")
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
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))
                    })
    })
    @GetMapping(value = "/messages", produces = "application/json")
    public Flux<RabbitResponse> list() {
        return Flux.just(new RabbitResponse(rabbitService.list()));
    }
}
